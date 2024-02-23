import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

// Класс ToyStore представляет магазин игрушек
public class ToyStore {
    // Очередь игрушек, отсортированная по весу
    PriorityQueue<Toy> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
    Random rand = new Random(); // Генератор случайных чисел

    // Метод для добавления новой игрушки в магазин
    public void put(String toyData) {
        // Разбиваем строку на части
        String[] data = toyData.split(" ");
        String id = data[0]; // идентификатор игрушки
        String name = data[1]; // имя игрушки
        int weight = Integer.parseInt(data[2]); // вес игрушки
        int quantity = Integer.parseInt(data[3]); // количество игрушек
        // Добавляем новую игрушку в очередь
        queue.add(new Toy(id, name, weight, quantity));
    }

    // Метод для изменения веса игрушки
    public void changeWeight(String id, int newWeight) {
        for (Toy toy : queue) {
            if (toy.id.equals(id)) {
                toy.weight = newWeight;
                break;
            }
        }
    }

    // Метод для получения игрушки по идентификатору
    public Toy getToyById(String id) {
        for (Toy toy : queue) {
            if (toy.id.equals(id)) {
                return toy;
            }
        }
        return null;
    }

    // Метод для выбора случайной игрушки из магазина
    public String get() {
        // Если очередь пуста, возвращаем null
        if (queue.isEmpty()) {
            return null;
        }

        // Список для хранения игрушек
        LinkedList<Toy> toys = new LinkedList<>();
        int totalWeight = 0; // общий вес всех игрушек

        // Перемещаем все игрушки из очереди в список
        while (!queue.isEmpty()) {
            Toy toy = queue.poll(); // извлекаем игрушку из очереди
            toys.add(toy); // добавляем игрушку в список
            totalWeight += toy.weight; // увеличиваем общий вес
        }

        int index = -1; // индекс выбранной игрушки
        int random = rand.nextInt(totalWeight); // генерируем случайное число

        // Выбираем игрушку с учетом ее веса
        for (int i = 0; i < toys.size(); ++i) {
            random -= toys.get(i).weight;
            if (random <= 0) {
                index = i;
                break;
            }
        }

        Toy selected = toys.get(index); // выбираем игрушку
        selected.quantity--; // уменьшаем количество выбранной игрушки
        if (selected.quantity == 0) {
            toys.remove(index); // удаляем выбранную игрушку из списка, если ее количество равно 0
        }
        queue.addAll(toys); // возвращаем оставшиеся игрушки обратно в очередь

        return selected.id; // возвращаем идентификатор выбранной игрушки
    }

    // Метод для записи результатов в файл
    public void writeToFile(String filename) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filename); // создаем объект для записи в файл
        // Записываем результаты работы метода get в файл
        for (int i = 0; i < 10; ++i) {
            Toy toy = getToyById(get());
            if (toy != null) {
                writer.println(toy.id + " " + toy.name);
            }
        }
        writer.close(); // закрываем файл
    }
}

