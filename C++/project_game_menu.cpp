#include <SFML/Graphics.hpp>
#include <SFML/Audio.hpp>
#include <cmath>
#include <vector>

/*
    DISCLAIMER: THIS IS AN UNFINISHED PROJECT
    CURRENTLY, THE "OPTIONS" FUNCTIONALITY DOES
    NOT DO ANYTHING
*/

float pi = 3.14159265358979323846;
sf::Font tf = sf::Font("/System/Library/Fonts/Supplemental/Comic Sans MS.ttf");

enum class Menu {
    MAIN,
    OPTIONS,
    QUIT
};

class Button {
    private:
        sf::ConvexShape body;
        sf::CircleShape corner;
        Menu menu;
        sf::Text text = sf::Text(tf);

    public:

        Button(){}

        Button(std::string text){
            this->text.setString(text);
        }

        Button(std::string text, Menu menu){
            this->text.setString(text);
            this->menu = menu;
        }

        sf::Vector2f getGeometricCenter() const {
            return body.getGeometricCenter();
        }

        sf::FloatRect getGlobalBounds() const {
            return body.getGlobalBounds();
        }

        void setSize(sf::Vector2f size){
            float radius = size.y / 2;

            body.setPointCount(100);

            for (int i = 0; i < 50; i++){
                body.setPoint(i, {-radius * sin(i * pi / 49), -radius * cos(i * pi / 49) + radius});
            }

            for (int i = 50; i < 100; i++){
                body.setPoint(i, {size.x + radius * sin((i-50) * pi / 49), radius * cos((i-50) * pi / 49) + radius});
            }

            text.setCharacterSize(std::min(size.x, size.y) * 0.45f);
        }

        void setFillColor(sf::Color color){
            body.setFillColor(color);
        }

        void setOutlineThickness(float thickness){
            body.setOutlineThickness(thickness);
        }

        void setOutlineColor(sf::Color color){
            body.setOutlineColor(color);
        }   

        void setOrigin(sf::Vector2f origin){
            body.setOrigin(origin);
        }

        void setPosition(sf::Vector2f position){
            body.setPosition(position);
        }

        void setTextColor(sf::Color color){
            text.setFillColor(color);
        }

        void setTextOutlineColor(sf::Color color){
            text.setOutlineColor(color);
        }

        void setTextOutlineThickness(float thickness){
            text.setOutlineThickness(thickness);
        }

        void setTextStyle(sf::Text::Style style){
            text.setStyle(style);
        }

        void setLetterSpacing(float space){
            text.setLetterSpacing(space);
        }

        void alignText(){
            sf::FloatRect b = text.getLocalBounds();
            text.setOrigin({b.position.x + b.size.x * 0.5f, b.position.y + b.size.y * 0.5f});
            text.setPosition(body.getPosition());
        }

        void displayIn(sf::RenderWindow& window){
            window.draw(body);
            window.draw(text);
        }

        void switchMenu(Menu& currentMenu){
            currentMenu = this->menu;
        }

};

// For the to-be-implemented OPTIONS menu
class Draggable {

};

int main(){

    sf::ContextSettings s;

    s.antiAliasingLevel = 4;

    sf::RenderWindow window(sf::VideoMode({1200, 1000}), "Knights of the Ten Castles", sf::State::Windowed, s);

    // BG Music
    // Relic Song (from Pokémon) is copyright unfortunately :(
    // so this is the replacement
    sf::SoundBuffer bf("sounds/dark.mp3");

    sf::Sound bg_music(bf);

    bg_music.setLooping(true);
    
    bg_music.setPitch(1.2f);

    bg_music.play();

    // Sound when pressed PLAY
    sf::SoundBuffer bf1("sounds/blocked.mp3");

    sf::Sound playsound(bf1);

    ////////////////////////////////////////////////

    // MAIN MENU BUTTONS
    Button play_b("PLAY", Menu::MAIN);
    Button options_b("OPTIONS", Menu::OPTIONS);
    Button quit_b("QUIT", Menu::QUIT);
    std::vector<Button> menu_buttons;
    menu_buttons.push_back(play_b);
    menu_buttons.push_back(options_b);
    menu_buttons.push_back(quit_b);

    // MAIN MENU
    sf::RectangleShape menu_bar;

    auto isHovering = [&](Button& b){
        if (b.getGlobalBounds().contains(sf::Vector2f(sf::Mouse::getPosition(window)))) return true;
        else return false;
    };

    auto draw_main = [&](){
        window.draw(menu_bar);
    };

    bool pressed[] = {false, false, false};

    ////////////////////////////////////////////////

    // QUIT MENU BUTTONS
    Button qyb("YES");
    Button qnb("NO", Menu::MAIN);
    std::vector<Button> quit_buttons;
    quit_buttons.push_back(qyb);
    quit_buttons.push_back(qnb);

    // QUIT MENU
    sf::RectangleShape quit_bar;
    sf::Text quit_text(tf);

    auto setQuitTextProperties = [&](){
        sf::Vector2f size = quit_bar.getSize();

        quit_text.setFillColor(sf::Color(255, 255, 255));
        quit_text.setLetterSpacing(1.25f);
        quit_text.setStyle(sf::Text::Italic);
        quit_text.setCharacterSize(std::min(size.x, size.y) * 0.12f);
        
    };

    auto draw_quit_text = [&](){
        // Draw first line
        quit_text.setString("Are you sure you");
        sf::FloatRect b = quit_text.getLocalBounds();
        quit_text.setOrigin({b.position.x + b.size.x * 0.5f, b.position.y});
        quit_text.setPosition({quit_bar.getPosition().x, quit_bar.getPosition().y - quit_bar.getSize().y * 0.35f});
        window.draw(quit_text);

        // Then second
        quit_text.setString("\nwant to quit?");
        b = quit_text.getLocalBounds();
        quit_text.setOrigin({b.position.x + b.size.x * 0.5f, b.position.y});
        quit_text.setPosition({quit_bar.getPosition().x, quit_bar.getPosition().y - quit_bar.getSize().y * 0.25f});
        window.draw(quit_text);
    };

    auto draw_quit = [&](){
        window.draw(quit_bar);
    };

    bool pressed1[] = {false, false};

    ////////////////////////////////////////////////

    // Customize menu bar & button properties
    auto setMenuProperties = [&](sf::RectangleShape& menu, sf::Vector2f size, sf::Vector2f position, float thickness){
        menu.setSize(size);
        menu.setOrigin(menu.getGeometricCenter());
        menu.setPosition(position);
        menu.setFillColor(sf::Color(75, 75, 75));
        menu.setOutlineThickness(thickness);
        menu.setOutlineColor(sf::Color::Black);
    };

    auto setButtonProperties = [&](Button& b, sf::Vector2f size, sf::Vector2f position, 
        float thickness, float t_thickness, float l_space, sf::Color fill_color){
        // For the button body itself
        b.setSize(size);
        b.setOrigin(b.getGeometricCenter());
        b.setPosition(position);
        b.setFillColor(fill_color);
        b.setOutlineThickness(thickness);
        b.setOutlineColor(fill_color);

        b.alignText();

        // For the text of the button
        b.setTextColor(sf::Color(255, 255, 255));
        b.setTextOutlineColor(sf::Color::Black);
        b.setTextOutlineThickness(t_thickness);
        b.setLetterSpacing(1.25f);
        b.setTextStyle(sf::Text::Bold);
    };

    ////////////////////////////////////////////////

    Menu current_menu = Menu::MAIN;

    while (window.isOpen()){

        if (std::optional event = window.pollEvent()){

            if (event->is<sf::Event::Closed>()){
                window.close();
            }

            else if (event->is<sf::Event::Resized>()){
                sf::View v(sf::FloatRect({0.f, 0.f}, sf::Vector2f(window.getSize())));

                window.setView(v);
            }

            // If pressed and is hovering, will 
            else if (auto* mouse = event->getIf<sf::Event::MouseButtonPressed>()){
                if (mouse->button == sf::Mouse::Button::Left){
                    switch (current_menu){
                        case Menu::MAIN:
                            for (int i = 0; i < menu_buttons.size(); i++){
                                if (isHovering(menu_buttons[i])){
                                    pressed[i] = true;
                                }
                            }
                            break;

                        case Menu::OPTIONS:
                            break;

                        case Menu::QUIT:
                            for (int i = 0; i < quit_buttons.size(); i++){
                                if (isHovering(quit_buttons[i])){
                                    pressed1[i] = true;
                                }
                            }
                            break;
                    
                    }
                }
            }

            else if (auto* mouse = event->getIf<sf::Event::MouseButtonReleased>()){
                if (mouse->button == sf::Mouse::Button::Left){
                    switch (current_menu){
                        case Menu::MAIN:
                            for (int i = 0; i < menu_buttons.size(); i++){
                                if (isHovering(menu_buttons[i]) && pressed[i]){
                                    if (i != 0) menu_buttons[i].switchMenu(current_menu);
                                    else playsound.play();
                                }
                                pressed[i] = false;
                            }
                            break;

                        case Menu::OPTIONS:
                            break;

                        case Menu::QUIT:
                            for (int i = 0; i < quit_buttons.size(); i++){
                                if (isHovering(quit_buttons[i]) && pressed1[i]){
                                    if (i != 0) quit_buttons[i].switchMenu(current_menu);
                                    else window.close();
                                }
                                pressed1[i] = false;
                            }
                            break;
                    
                    }
                }
            }

        }

        // Window sizes (for drawing objects)
        int w_min = std::min(window.getSize().x, window.getSize().y);
        int w_x = window.getSize().x;
        int w_y = window.getSize().y;

        window.clear(sf::Color(50, 50, 50));

        // Checks for what menu currently on
        switch (current_menu){
            case Menu::MAIN:
                setMenuProperties(menu_bar, {w_x * 0.285f, w_y * 0.4875f}, {w_x * 0.5f, w_y * 0.5f}, -w_min * 0.00625f);

                draw_main();

                setButtonProperties(menu_buttons[1], {w_x * 0.125f, w_min * 0.08125f}, 
                {w_x * 0.5f, w_y * 0.5f}, -w_min * 0.00625f, -w_x * 0.00125f, w_x * 0.00125f, sf::Color::Black);

                setButtonProperties(menu_buttons[0], {w_x * 0.125f, w_min * 0.08125f}, 
                {w_x * 0.5f, w_y * 0.5f - menu_bar.getSize().y * 0.25f}, -w_min * 0.00625f, -w_min * 0.00125f, w_x * 0.00125f, sf::Color::Black);

                setButtonProperties(menu_buttons[2], {w_x * 0.125f, w_min * 0.08125f}, 
                {w_x * 0.5f, w_y * 0.5f + menu_bar.getSize().y * 0.25f}, -w_min * 0.00625f, -w_min * 0.00125f, w_x * 0.00125f, sf::Color::Black);

                for (int i = 0; i < menu_buttons.size(); i++){
                    if (isHovering(menu_buttons[i])){
                        menu_buttons[i].setTextColor(sf::Color::Black);
                        menu_buttons[i].setFillColor(sf::Color(255, 255, 255));
                    }

                    if (pressed[i]){
                        menu_buttons[i].setTextColor(sf::Color::Black);
                        menu_buttons[i].setFillColor(sf::Color(192, 192, 192));
                    }

                    menu_buttons[i].displayIn(window);
                }

                break;
            
            case Menu::OPTIONS:
                // TENTATIVE CODE FOR NOW
                current_menu = Menu::MAIN;
                break;

            case Menu::QUIT:
                sf::Color cols[] = {sf::Color(0, 255, 0), sf::Color(255, 0, 0)};

                setMenuProperties(quit_bar, {w_x * 0.285f, w_y * 0.24375f}, {w_x * 0.5f, w_y * 0.5f}, -w_min * 0.00625f);
                
                setQuitTextProperties();

                setButtonProperties(quit_buttons[1], {w_x * 0.025f, w_min * 0.08125f * 1.2f}, 
                quit_bar.getPosition() + sf::Vector2f({quit_bar.getSize().x * 0.25f, quit_bar.getSize().y * 0.21f}), 
                -w_min * 0.00625f, -w_x * 0.00125f, w_x * 0.00125f, cols[1]);

                setButtonProperties(quit_buttons[0], {w_x * 0.025f, w_min * 0.08125f * 1.2f}, 
                quit_bar.getPosition() - sf::Vector2f({quit_bar.getSize().x * 0.25f, -quit_bar.getSize().y * 0.21f}), 
                -w_min * 0.00625f, -w_x * 0.00125f, w_x * 0.00125f, cols[0]);

                draw_quit();
                draw_quit_text();

                for (int i = 0; i < 2; i++){
                    if (isHovering(quit_buttons[i])){
                        quit_buttons[i].setTextColor(cols[i]);
                        quit_buttons[i].setFillColor(sf::Color(255, 255, 255));
                    }

                    if (pressed1[i]){
                        quit_buttons[i].setTextColor(cols[i]);
                        quit_buttons[i].setFillColor(sf::Color(192, 192, 192));
                    }

                    quit_buttons[i].displayIn(window);
                }

                break;

        }
        
        window.display();
    }

    return 0;
}