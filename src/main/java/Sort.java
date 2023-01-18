import java.io.PrintStream;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Sort implements Runnable{
    private static PrintStream printStream = new PrintStream(System.out);
    private static List<Integer> arrayList = initilizeArray();
    private static List<Integer> arrayList2 = initilizeArray();
    private static List<Integer> arrayList3 = initilizeArray();
    private static List<Integer> arrayList4 = initilizeArray();
    private static List<Integer> arrayList5 = initilizeArray();

    public static void main(String[] args) throws InterruptedException {
        List<Integer> resultList = bubbleSort(arrayList);
        List<Integer> resultList2 = selectionSort(arrayList2);
        List<Integer> resultList3 = insertionSort(arrayList3);
        List<Integer> resultList4 = shuttleSort(arrayList4);
        List<Integer> resultList5 = shellSort(arrayList5);

    }

    public static List<Integer> initilizeArray(){
        return new Random()
                .ints(150000, 1, 150000)
                .boxed()
                .collect(Collectors.toList());
    }

    public static List<Integer> collectionSort(List<Integer> list) {
        Instant time = Instant.now();
        Collections.sort(list);
        Instant time2= Instant.now();
        printStream.println("Сортировка Collections.sort заняла секунд: " + (time2.getEpochSecond() - time.getEpochSecond()));
        return list;
    }

    //сложность алгоритма O(n^2), неизвестно сколько понадобится итераций
    public static List<Integer> bubbleSort(List<Integer> list) throws InterruptedException {
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                Instant time = Instant.now();
                int temp;
                boolean sorted = false;
                while (!sorted) {
                    sorted = true;
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i) > list.get(i + 1)) {
                            temp = list.get(i);
                            list.set(i, list.get(i + 1));
                            list.set(i + 1, temp);
                            sorted = false;
                        }
                    }
                }
                Instant time2 = Instant.now();
                printStream.println("Сортировка Пузырьком заняла секунд: " + (time2.getEpochSecond() - time.getEpochSecond()));
            }
        };
        thread1.start();
        return list;
    }

    //Сортировка выбором O(n^2), сортировка неустойчива, так как одинаковые элементы могут изменить свое положение
    public static List<Integer> selectionSort(List<Integer> list) throws InterruptedException {
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                Instant time = Instant.now();
                for (int i = 0; i < list.size(); i++) {
                    int min = i;
                    for (int j = i; j < list.size(); j++) {
                        if (list.get(j) < list.get(min)) {
                            min = j;
                        }
                    }
                    swap(list, i, min);
                }
                Instant time2 = Instant.now();
                printStream.println("Сортировка выбором заняла секунд: " + (time2.getEpochSecond() - time.getEpochSecond()));
            }
        };
        thread2.start();
        return list;
    }

    private static synchronized void swap(List<Integer> list, int i1, int i2) {
        int tmp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, tmp);
    }

    //Сортировка вставками O(n^2), устойчивая сортировка
    public static List<Integer> insertionSort(List<Integer> list) throws InterruptedException {
        Thread thread3 = new Thread(){
            @Override
            public void run() {
                Instant time = Instant.now();
                for (int i = 0; i < list.size(); i++) {
                    int value = list.get(i); // Вытаскиваем значение элемента
                    int x = i - 1; // Перемещаемся по элементам, которые перед вытащенным элементом
                    for (; x >= 0; x--) {
                        if (value < list.get(x)) { // Если вытащили значение меньшее — передвигаем больший элемент дальше
                            list.set(x + 1, list.get(x));
                        } else break; // Если вытащенный элемент больше — останавливаемся
                    }
                    list.set(x + 1, value); // В освободившееся место вставляем вытащенное значение
                }
                Instant time2 = Instant.now();
                printStream.println("Сортировка вставками заняла секунд: " + (time2.getEpochSecond() - time.getEpochSecond()));
            }
        };
        thread3.start();
        return list;
    }

    // Челночная сортировка (итерация идет слева направо, при перестановке выполняем проверку элементов позади)
    public static List<Integer> shuttleSort(List<Integer> list) throws InterruptedException {
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                Instant time = Instant.now();
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i) < list.get(i - 1)) {
                        swap(list, i, i - 1);
                        for (int j = i - 1; (j - 1) >= 0; j--) {
                            if (list.get(j) < list.get(j - 1)) {
                                swap(list, j, j - 1);
                            } else break;

                        }
                    }
                }
                Instant time2 = Instant.now();
                printStream.println("Челночная сортировка заняла секунд: " + (time2.getEpochSecond() - time.getEpochSecond()));
            }
        };
        thread4.start();
        return list;
    }

    //Сортировка Шелла (каждую итерацию разный промежуток сравниваемых элементов, уменьшается вдвое)
    public static List<Integer> shellSort(List<Integer> list) throws InterruptedException {
        Thread thread5 = new Thread(){
            @Override
            public void run() {
                Instant time = Instant.now();
                int value = list.size() / 2; // Высчитываем промежуток между проверяемыми элементами
                while (value >= 1) {
                    for (int i = 0; i < list.size(); i++) {
                        for (int j = i - value; j >= 0; j -= value) {
                            // Смещаем правый указатель, пока не сможем найти такой, что
                            // между ним и элементом до него не будет нужного промежутка
                            if (list.get(j) > list.get(j + value))
                                swap(list, j, j + value);
                        }
                    }
                    value = value / 2;
                }
                Instant time2 = Instant.now();
                printStream.println("Сортировка Шелла заняла секунд: " + (time2.getEpochSecond() - time.getEpochSecond()));
            }
        };
        thread5.start();
        return list;
    }

    @Override
    public void run() {

    }
}