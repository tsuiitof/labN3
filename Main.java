import java.util.*;
public class Main {
    public static void main(String[] args) {
        // Создаём граф
        Graph graph = new Graph();

        // Добавление рёбер
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 3); // Петля
        graph.addEdge(4, 4); // Петля
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);

        // 1. Вывод рёбер
        System.out.println("Список всех рёбер:");
        graph.printEdges();

        // 2. Добавление нового ребра
        graph.addEdge(7, 8);
        System.out.println("Добавлено новое ребро (7 → 8). Список рёбер:");
        graph.printEdges();

        // 3. Вывод вершин по убыванию номера
        System.out.println("Вершины по убыванию номера:");
        graph.printVerticesDescending();

        // 4. Вершины с входящими рёбрами больше заданного числа
        System.out.println("Вершины с входящими рёбрами больше 1:");
        graph.getVerticesMore(1);

        // 5. Удаление ребра
        graph.removeEdge(6, 7);
        System.out.println("Ребро (6 → 7) удалено. Список рёбер:");
        graph.printEdges();

        // 6. Удаление вершины
        graph.removeVertex(5);
        System.out.println("Вершина 5 удалена. Список рёбер:");
        graph.printEdges();

        // 7. Удаление вершин с наименьшим количеством входящих рёбер
        graph.removeVerticesWithMinIncomingEdges();
        System.out.println("Вершины с наименьшим количеством входящих рёбер удалены. Список рёбер:");
        graph.printEdges();

        // 8. Подсчёт количества компонент связности
        System.out.println("Количество компонент связности: " + Vertices.countConnectedComponents(graph));

        // 9. Поиск компонент связности
        System.out.println("Компоненты связности: " + Vertices.getConnectedComponents(graph));

        // 10. Вершины, достижимые за 2 хода
        System.out.println("Вершины, достижимые за 2 хода из вершины 1: " + Vertices.getReachableVerticesIn2Moves(graph, 1));

        // 11. Вершины, достижимые за заданное количество ходов
        System.out.println("Вершины, достижимые за 3 хода из вершины 1:");
        Set<Integer> reachableInThreeSteps = Vertices.getVerticesReachableInKSteps(graph, 1, 3);
        System.out.println(reachableInThreeSteps);

        // 12. Сложение двух графов
        Graph graph2 = new Graph();
        graph2.addEdge(9, 10);
        graph2.addEdge(10, 11);
        Graph mergedGraph = graph.mergeGraphs(graph2);
        System.out.println("Объединённый граф. Список рёбер:");
        mergedGraph.printEdges();

        // 13. Проверка на полноту графа
        System.out.println("Граф полный? " + graph.isComplete());

        // 14. Проверка на пустоту графа
        System.out.println("Граф пустой? " + graph.isEmpty());

        // 15. Проверка на наличие вершин, соединённых только с собой
        Map<Integer, Set<Integer>> adjacencyList = graph.buildAdjacencyList();
        System.out.println("Есть ли вершины, соединённые только с собой? " + new Vertices().hasVerticesWithOnly(adjacencyList));
    }
}
