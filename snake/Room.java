package net.snake;

import java.awt.event.KeyEvent;

public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    public static Room game;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {return height;}

    public void setHeight(int height) {
        this.height = height;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }



    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();   //двигаем змею
            print();        //отображаем текущее состояние игры
            sleep();        //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    void createMouse() {
        mouse = new Mouse((int) (Math.random() * width), (int) (Math.random() * height));
    }

    public void eatMouse() {
        createMouse();
    }

    public void sleep() {
        try {
            int snakeSize = snake.getSections().size();
            if (snakeSize >= 15) {
                Thread.sleep(200);
            } else if (snakeSize >= 11){
                Thread.sleep(300);
            } else {
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {

        }
    }

    public void print() {
        int[][] field = new int[height][width]; //массив, куда будем "рисовать" текущее состояние игры
        field[snake.getY()][snake.getX()] = 2;  // голова
        field[mouse.getY()][mouse.getX()] = 3;  // мышь

        for (SnakeSection s : snake.getSections()) { //Рисуем все кусочки змеи
            field[s.getY()][s.getX()] = 1; // тело
        }

        for ( int y = 0; y < height; y++){ //Рисуем мышь
            for (int x = 0; x < width; x++){
                if(field[y][x] == 1) System.out.print("x");  //  тело
                else if(field[y][x] == 2) System.out.print("X");  //  голова
                else if(field[y][x] == 3)  System.out.print("^"); //  мышь
                else System.out.print(".");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Snake snake1 = new Snake(0, 0);
        game = new Room(400, 400, snake1);
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }
}
