package com.javarush.task.task23.task2312;


import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Основной класс программы.
 */
public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
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

    /**
     * Выводим на экран текущее состояние игры
     */
    public void print() {
/*
        int[][] matrix = new int[height][width];
        matrix[snake.getSections().get(0).getY()][snake.getSections().get(0).getX()] = 2;
        for (int i = 1; i < snake.getSections().size(); i++) {
            matrix[snake.getSections().get(i).getY()][snake.getSections().get(i).getX()] = 1;
        }
        matrix[mouse.getY()][mouse.getX()] = 3;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) System.out.print(" x ");
                else if (matrix[i][j] == 2) System.out.print(" X ");
                else if (matrix[i][j] == 3) System.out.print(" * ");
                else if (matrix[i][j] == 0) System.out.print(" . ");
            }
            System.out.println();
        }*/

        //Создаем массив, куда будем "рисовать" текущее состояние игры
        int[][] matrix = new int[height][width];


        //Рисуем все кусочки змеи
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }
        //Рисуем голову змеи (4 - если змея мертвая)
        matrix[sections.get(0).getY()][sections.get(0).getX()] = snake.isAlive() ? 2 : 4;
        //Рисуем мышь
        matrix[mouse.getY()][mouse.getX()] = 3;

        //Выводим все это на экран
//        String[] symbols = {" . ", " x ", " X ", "^_^", "RIP"};
//
//        for (int y = 0; y < height; y++)
//        {
//            for (int x = 0; x < width; x++)
//            {
//                System.out.print(symbols[matrix[y][x]]);
//            }
//            System.out.println();
//        }
//
//        System.out.println();
//        System.out.println();
//        System.out.println();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (matrix[i][j]) {
                    case 0:
                        System.out.print(" . ");
                        break;
                    case 1:
                        System.out.print(" x ");
                        break;
                    case 2:
                        System.out.print(" X ");
                        break;
                    case 3:
                        System.out.print("^_^");
                        break;
                    case 4:
                        System.out.print("RIP");
                        break;
                }
            }
            System.out.println();
        }
    }

    /**
     * Метод вызывается, когда мышь съели
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * Создает новую мышь
     */
    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }


    public static Room game;

    public static void main(String[] args) {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }


    /**
     * Программа делает паузу, длинна которой зависит от длинны змеи.
     */

    public void sleep() {
        try {
            int sleepTime = 500;
            int level = snake.getSections().size();
            if (level == 1) {
                sleepTime = 500;
            } else if (level > 14) {
                sleepTime = 200;
            } else {
                sleepTime = sleepTime - (20 * (level - 1));
            }
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
