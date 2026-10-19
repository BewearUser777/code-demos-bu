#include <iostream>
#include <cmath>
#include <array>
#include <thread>
#include <chrono>

using std::cout;
using std::endl;
using std::sin;
using std::cos;
using std::log;
using std::abs;
using std::array;
using namespace std::chrono_literals;

array<float, 2> solve_max(float start = 0.1, float end = 1.5, float delta = 0.1, float R_start = 0.1, 
    float R_end = 10.0, float R_delta = 0.01, bool print_process = false, float delay = 0.0){
    if (start <= 0.0 || end >= 1.57 || R_start <= 0.0 || delay <= 0.0){
        cout << "You have one or more invalid values. Please try again.\n";
        return {-1.0, -1.0};
    }
    float max = start;
    float R_max = R_start;
    float i_R = start;
    float last_max_ago = 0;
    for (float i = start; i <= end; i+=delta){
        float R_closest = R_start;
        // Completely random and large arbitrary value
        // to guarantee the min_threshold won't remain
        // this value
        float min_threshold = 4206967;
        for (float R = R_start; R <= R_end; R+=R_delta){
            float lhs = ((sin(i) + 1) / cos(i)) * R;
            float rhs = -log(1-(R / cos(i)));
            float closeness = abs(lhs-rhs);
            if (closeness < min_threshold){
                R_closest = R;
                min_threshold = closeness;
            }
        }
        if (R_closest > R_max){
            R_max = R_closest;
            i_R = i;
            last_max_ago = 0;
        } 
        // Ensures that once the max is found, program won't try to
        // find other maxima that don't exist
        else last_max_ago++;

        // If max hasn't been updated in 10 angle iterations, that is most likely the max range
        if (last_max_ago > 9) break;
        if (print_process) cout << "Current max range: " << R_max << "\nAngle of max range: " << i_R << '\n';
        std::this_thread::sleep_for(delay * 1ms);
    }

    return {R_max, i_R};
}

int main(){

    // For testing which angle has largest range
    float angle_start = 0.1;
    float angle_end = 1.5;

    // Higher delta = higher accuracy
    float angle_delta = 0.001;

    // For solving the transcendental equation
    float range_start = 0.1;
    float range_end = 10.0;

    // Higher delta = more accurate solution
    float range_delta = 0.0001;

    bool show_process = true;

    // If want to see results not output in nanoseconds
    float time_delay_milliseconds = 10.0;

    array<float, 2> max_vals = solve_max(angle_start, angle_end, angle_delta, range_start, range_end, 
        range_delta, show_process, time_delay_milliseconds);

    cout << endl << "Maximum range: " << max_vals[0] << endl;
    cout << "Angle of maximum range: " << max_vals[1] << endl;

    return 0;
}