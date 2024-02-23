import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        ToyStore store = new ToyStore(); // создаем магазин игрушек
        // добавляем игрушки в магазин
        store.put("1 конструктор 2 10");
        store.put("2 робот 2 10");
        store.put("3 кукла 6 10");

        // Пытаемся записать результаты работы метода get в файл
        try {
            store.writeToFile("output.txt");
        } catch (FileNotFoundException e) {
            // Если файл не найден, выводим сообщение об ошибке
            System.out.println("Ошибка: файл не найден.");
            e.printStackTrace(); // выводим стек вызовов исключения
        }
    }
}


