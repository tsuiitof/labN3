import java.util.*;
class ShowGraph {
    private final Graph graph;

    // Конструктор принимает объект Graph
    public ShowGraph(Graph graph) {
        this.graph = graph;
    }

    // Отображение всех рёбер графа
    public void displayEdges() {
        System.out.println("Рёбра графа:");
        for (Edge edge : graph.printEdges()) // Проходимся по каждому элементу из коллекции ребер
            System.out.println(edge);
    }

    // Отображение всех вершин графа по убыванию
    public void displayVerticesDescending() {
        System.out.println("Вершины в порядке убывания:");
        for (int vertex : graph.printVerticesDescending()) // Перебираем все вершины
            System.out.print(vertex + " ");
        System.out.println(); // Пустая строка для разделения выводов
    }

    // Отображение вершин с количеством входящих рёбер больше заданного числа
    public void getVerticesMore(int x) {
        System.out.println("Вершины с количеством входящих рёбер больше " + x + ":");
        for (int vertex : graph.getVerticesMore(x)) // Перебираем вершины
            System.out.print(vertex + " ");
        System.out.println(); // Пустая строка для разделения выводов
    }

    // Отображение списка компонент связности
    public void getConnectedComponents() {
        System.out.println("Компоненты связности:");
        List<List<Integer>> components = graph.getConnectedComponents(graph); // Вызываем метод getConnectedComponents объекта graph.
        System.out.println(components);
    }

    // Отображение количества компонент связности
    public void displayConnectedComponentsCount() {
        // Получаем список компонент связности
        List<List<Integer>> components = graph.getConnectedComponents(graph); // аналогично
        System.out.println("Количество компонент связности: " + components.size());
    }

    // Отображение вершин, достижимых за 2 хода
    public void displayReachableInTwoSteps(int start) {
        System.out.println("Вершины, достижимые из " + start + " за 2 хода:");
        for (int vertex : Graph.getReachableVerticesIn2Moves(graph, start)) // Проходимся по списку вершин
            System.out.print(vertex + " ");
        System.out.println(); // Пустая строка для разделения выводов
    }

    // Отображение вершин, достижимых за указанное количество ходов
    public void displayReachableInSteps(int start, int steps) {
        System.out.println("Вершины, достижимые из " + start + " за " + steps + " ходов:");
        for (int vertex : Graph.getVerticesReachableInKSteps(graph, start, steps)) // Проходимся по списку вершин
            System.out.print(vertex + " ");
        System.out.println(); // Пустая строка для разделения выводов
    }

    // Отображение свойств графа (проверка на полноту, пустоту и наличие только петель)
    public void displayGraphProperties() {
        Map<Integer, Set<Integer>> adjacencyList = graph.buildAdjacencyList();
        System.out.println("Свойства графа:");
        System.out.println("Граф полный? " + graph.isComplete());
        System.out.println("Граф пустой? " + graph.isEmpty());
        System.out.println("Граф содержит вершины с петлями? " + graph.hasVerticesWithOnly(adjacencyList));
    }
}
