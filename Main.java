import java.util.Scanner;
class Main {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        // Создаем граф
        Graph graph = new Graph();

        // Добавляем рёбра в граф
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(1, 3);
        graph.addEdge(3, 1); // Петля
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 4);

        // Создаем объект для отображения графа
        ShowGraph showGraph = new ShowGraph(graph);

        // Отображение всех рёбер
        showGraph.displayEdges();

        // Отображение всех вершин по убыванию
        showGraph.displayVerticesDescending();

        // Отображаем вершины, достижимые за 2 хода из вершины k
        showGraph.displayReachableInTwoSteps(2);

        // Отображаем вершины, достижимые за n ходов из вершины k
        showGraph.displayReachableInSteps(3, 5);

        // Отображение вершин с количеством входящих рёбер больше k
        showGraph.getVerticesMore(1);

        // удаляем ребро
        graph.removeEdge(4, 5);
        showGraph.displayEdges();

        // Отображение количества компонент графа
        showGraph.displayConnectedComponentsCount();

        // Отображение компонент связности
        showGraph.getConnectedComponents();

        // Удаляем вершину и повторно отображаем граф
        graph.removeVertex(3);
        showGraph.displayEdges();

        // Удаляем вершину с мин. кол-вом ребер и повторно отображаем граф
        graph.removeVerticesWithMinIncomingEdges();
        showGraph.displayEdges();

        // Добавляем несколько новых рёбер
        graph.addEdge(1, 5);
        graph.addEdge(5, 7);
        graph.addEdge(7, 8);
        showGraph.displayEdges();

        // Отображение свойств графа
        showGraph.displayGraphProperties();

        // Создаем второй граф
        Graph graph2 = new Graph();
        graph2.addEdge(8, 9);
        graph2.addEdge(9, 10);

        // Объединяем графы
        graph.sumGraphs(graph2);
        showGraph.displayEdges();
    }
}
