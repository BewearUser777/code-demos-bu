#include <SFML/Graphics.hpp>
#include <thread>
#include <chrono>

using namespace std::chrono_literals;

int main(){

    sf::ContextSettings cos;
    cos.antiAliasingLevel = 4;

    sf::RenderWindow w(sf::VideoMode({1200,800}), "BallSaver1", sf::State::Windowed, cos);

    sf::CircleShape ball(75.f);
    ball.setOrigin({75.f, 75.f});
    ball.setPosition({75.f, 75.f});
    ball.setFillColor(sf::Color::Red);

    int w_min = std::min(w.getSize().x, w.getSize().y);

    float v_x = 1;
    float v_y = 1;

    while (w.isOpen()){

        while (auto event = w.pollEvent()){

            if (event->is<sf::Event::Closed>()){
                w.close();
            }

            else if (event->is<sf::Event::Resized>()){
                sf::View v(sf::FloatRect({0.f, 0.f}, sf::Vector2f(w.getSize())));
                w.setView(v);
                w_min = std::min(w.getSize().x, w.getSize().y);
                ball.setRadius(w_min * 3 / 32);
                ball.setPosition({(float) ball.getRadius(), (float) ball.getRadius()});
                ball.setOrigin({(float) ball.getRadius(), (float) ball.getRadius()});
            }
        }

        if (ball.getPosition().x <= ball.getRadius()){
            v_x = 1.f;
        }

        if (ball.getPosition().y <= ball.getRadius()){
            v_y = 1.f;
        }

        if (ball.getPosition().x >= w.getSize().x - ball.getRadius()){
            v_x = -1.f;
        }

        if (ball.getPosition().y >= w.getSize().y - ball.getRadius()){
            v_y = -1.f;
        }

        ball.setPosition(ball.getPosition() + sf::Vector2f({v_x, v_y}));

        w.clear(sf::Color::Green);

        w.draw(ball);

        w.display();

        std::this_thread::sleep_for(1ms);
    }

    return 0;
}