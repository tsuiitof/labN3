public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(20, 20);

        // Добавление рёбер
        graph.addEdge(1, 2);
        graph.addEdge(2, 4);
        graph.addEdge(1, 3);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        graph.addEdge(3, 7);
        graph.addEdge(4, 8);
        graph.addEdge(5, 8);
        graph.addEdge(6, 9);
        graph.addEdge(7, 9);
        graph.addEdge(8, 10);
        graph.addEdge(9, 10);

        // Выводим список рёбер
        graph.printEdges();

        // Выводим вершины по убыванию
        graph.printVerticesDescending();

        // Поиск вершин с входящими рёбрами больше заданного числа
        graph.printVerticesWithIncomingEdgesMoreThan(1);

        // 5. Удаление ребра
        graph.removeEdge(1, 5);
        graph.printEdges(); // Проверяем удаление

        // 6. Удаление вершины
        graph.removeVertex(5);
        graph.printEdges(); // Проверяем удаление

        // 7. Удаление вершин с наименьшим числом входящих рёбер
        graph.removeVerticesWithFewestIncomingEdges();
        graph.printEdges();

        System.out.println("Создан новый граф");
        Graph graph1 = new Graph(30, 20);
        // Добавление рёбер
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 4);
        graph1.addEdge(1, 3);
        graph1.addEdge(2, 5);
        graph1.addEdge(3, 6);
        graph1.addEdge(3, 7);
        graph1.addEdge(4, 8);
        graph1.addEdge(5, 8);
        graph1.addEdge(6, 9);
        graph1.addEdge(7, 9);
        graph1.addEdge(8, 10);
        graph1.addEdge(9, 10);

        // Выводим список рёбер
        graph1.printEdges();

        // 8. Подсчёт компонент связности
        graph1.countConnectedComponents();

        // 9. Поиск компонент связности
        graph1.findConnectedComponents();

        // 10. Вывод вершин, достижимых за 2 шага из указанной вершины
        graph1.printVerticesReachableInTwoSteps(2);

        // 11. Вывод вершин, достижимых за не более чем n шага из указанной вершины
        graph1.printVerticesReachableInSteps(1, 4);

        // Создание второго графа для объединения
        Graph graph2 = new Graph(20, 20);
        graph2.addEdge(4, 3);
        graph2.addEdge(6, 5);
        graph2.addEdge(5, 6);

        // 12. Объединение графов
        graph1.mergeGraph(graph2);
        graph1.printEdges();

        // 13. Проверка графа на полноту
        graph1.isCompleteGraph();

        // 14. Проверка графа на пустоту
        graph1.isEmptyGraph();

        // 15. Наличие петель в графе
        graph1.hasSelfLoop();
    }
}
