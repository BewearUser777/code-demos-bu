#include <iostream>
#include <ctime>

using std::cout;
using std::cin;
using std::string;
using std::vector;

// Precondition: n by n matrix
int det(vector<vector<double>> m){

    int size = m.size();

    // Special cases
    if (size == 1) return m[0][0];
    if (size == 2) return m[0][0] * m[1][1] - m[1][0] * m[0][1];

    // Initialize vector
    vector<vector<double>> sub_m;
    for (int i = 0; i < size - 1; i++){
        sub_m.push_back({});
        for (int j = 0; j < size - 1; j++){
            sub_m[i].push_back(0);
        }
    }

    // Recursive determinant
    double determinant = 0;
    for (int x = 0; x < size; x++){

        for (int i = 1; i < size; i++){
            for (int j = 0; j < size; j++){
                if (j != x){
                    if (j > x) sub_m[i-1][j-1] = m[i][j];
                    else sub_m[i-1][j] = m[i][j];
                }
            }
        }

        double result = m[0][x] * det(sub_m);

        x % 2 == 0 ? determinant += result : determinant -= result;

    } 

    return determinant;
}

// Precondition: m1[0].size() == m2.size(), rectangular vectors
vector<vector<double>> operator*(vector<vector<double>> m1, vector<vector<double>> m2){
    vector<vector<double>> prod;

    for (int i = 0; i < m1.size(); i++){
        prod.push_back({});
        for (int j = 0; j < m2[0].size(); j++){
            prod[i].push_back(0);
        }
    }

    for (int i = 0; i < m1.size(); i++){
        for (int j = 0; j < m2[0].size(); j++){
            for (int shared = 0; shared < m1[i].size(); shared++){
                prod[i][j] += m1[i][shared] * m2[shared][j];
            }
        }
    }

    return prod;
}

// Precondition: m1.size() = m2.size() && m1[0].size() = m2(0).size(), rectangular vectors
vector<vector<double>> operator+(vector<vector<double>> m1, vector<vector<double>> m2){
    vector<vector<double>> sum;
    for (int i = 0; i < m1.size(); i++){
        sum.push_back({});
        for (int j = 0; j < m1[i].size(); j++){
            sum[i].push_back(0);
        }
    }

    for (int i = 0; i < sum.size(); i++){
        for (int j = 0; j < sum[i].size(); j++){
            sum[i][j] = m1[i][j] + m2[i][j];
        }
    }

    return sum;
}

void initialize_matrix(vector<vector<double>>& m){
    int x;
    int y;
    cout << "Enter horizontal matrix dimension (must be greater than 0): ";
    cin >> x;
    cout << "Enter vertical matrix dimension (must be greater than 0): ";
    cin >> y;
    cout << '\n';

    if (x < 1 || y < 1) throw "Error: matrix dimensions cannot be less than 1";
        
    for (int i = 0; i < y; i++){
        m.push_back({});
        for (int j = 0; j < x; j++){
            double value;
            cout << "Enter a value for row " << i + 1 << ", column " << j + 1 << ": ";
            cin >> value;
            m[i].push_back(value);
        }
    }

    cout << "\nYour matrix looks like this:\n";
        
    for (int i = 0; i < m.size(); i++){
        for (int j = 0; j < m[i].size(); j++){
            cout << m[i][j] << ' ';
        }
        cout << '\n';
    }
}

void show_det(vector<vector<double>> m){
    cout << "Determinant: ";
    if (m.size() == m[0].size()){
        cout << det(m);
    } else {
        cout << "N/A";
    }
}

void show_product(vector<vector<double>> m1, vector<vector<double>> m2){
    cout << "Product Matrix: ";
    if (m1[0].size() == m2.size()){
        vector<vector<double>> product = m1 * m2;
        cout << '\n';
        for (int i = 0; i < product.size(); i++){
            for (int j = 0; j < product[i].size(); j++){
                cout << product[i][j] << ' ';
            }
            cout << '\n';
        }
        cout << '\n';
    } else {
        cout << "N/A\n\n";
    }
}

void show_sum(vector<vector<double>> m1, vector<vector<double>> m2){
    cout << "Sum Matrix: ";
    if (m1.size() == m2.size() && m1[0].size() == m2[0].size()){
        vector<vector<double>> sum = m1 + m2;
        cout << '\n';
        for (int i = 0; i < sum.size(); i++){
            for (int j = 0; j < sum[i].size(); j++){
                cout << sum[i][j] << ' ';
            }
            cout << '\n';
        }
    } else {
        cout << "N/A\n\n";
    }

}

int main(){
    srand(time(0));

    while (true){
        vector<vector<double>> m1;
        vector<vector<double>> m2;

        char option;
        cout << "Type \"q\" or type \"c\" to continue: ";
        cin >> option;
        if (option == 'q') break;
        else if (option == 'c'){
            try {
                cout << "\nMatrix 1\n";
                initialize_matrix(m1);

                cout << "\nMatrix 2\n";
                initialize_matrix(m2);

                cout << "\nMatrix 1\n";
                show_det(m1);

                cout << "\nMatrix 2\n";
                show_det(m2);

                cout << "\n\n";

                show_product(m1, m2);

                show_sum(m1, m2);
            }
            catch (char const* e){
                cout << e << "\n\n";
            }
        }
        else {
            cout << "Invalid input.\n\n";
        }

    }



    return 0;
}

// TEST CODE PURPOSES ONLY
    /*
    vector<vector<double>> matrix;
    int size = 5;

    for (int i = 0; i < size; i++){
        matrix.push_back({});
        for (int j = 0; j < size; j++){
            int random = rand() % 10;
            if (random < 5) random = -random;
            matrix[i].push_back(random);
        }
    }

    for (int i = 0; i < size; i++){
        for (int j = 0; j < size; j++){
            cout << matrix[i][j] << ' ';
        }
        cout << '\n';
    }

    vector<vector<double>> matrix_2 = {{2,5},{3,7},{4,8},{9,3},{1,1}};

    cout << det(matrix) << '\n';

    vector<vector<double>> p = matrix * matrix_2;

    for (int i = 0; i < p.size(); i++){
        for (int j = 0; j < p[i].size(); j++){
            cout << p[i][j] << ' ';
        }
        cout << '\n';
    }
    */