import java.util.*;

public class Graph {
    private final Set<Integer> vertices; // Множество вершин графа
    private final List<Edge> edges;      // Список рёбер графа

    // Конструктор для создания пустого графа.
    public Graph() {
        this.vertices = new HashSet<>();
        this.edges = new ArrayList<>();
    }

    // 1. Вывод всех рёбер графа в порядке их добавления.
    public List<Edge> printEdges() {
        return new ArrayList<>(edges); // Возвращает копию списка рёбер
    }

    // 2. Добавление нового ребра в граф.
    public void addEdge(int from, int to) {
        Edge newEdge = new Edge(from, to); // Создаём новое ребро
        if (!edges.contains(newEdge)) { // Проверяем, уникально ли ребро
            edges.add(newEdge); // Добавляем ребро
            vertices.add(from); // Добавляем вершины в множество (если они отсутствуют)
            vertices.add(to);
        }
    }

    // 5. Удаление указанного ребра из графа.
    public void removeEdge(int from, int to) {
        Edge edgeToRemove = new Edge(from, to); // Создаём объект ребра для удаления
        edges.remove(edgeToRemove); // Удаляем ребро из списка (если оно существует)
    }

    // 6. Удаление вершины из графа, включая все её входящие и исходящие рёбра.
    public void removeVertex(int vertex) { // vertex - вершина для удаления
        // Удаляем рёбра, связанные с данной вершиной
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            if (edge.getFrom() == vertex || edge.getTo() == vertex) {
                iterator.remove();
            }
        }
        // Удаляем вершину
        vertices.remove(vertex);
    }

    // 3.Вывод списка вершин графа, отсортированного по убыванию.
    public List<Integer> printVerticesDescending() {
        List<Integer> sortedVertices = new ArrayList<>(vertices); // Создаём копию множества вершин
        sortedVertices.sort(Collections.reverseOrder()); // Сортируем по убыванию
        return sortedVertices; // Возвращаем отсортированный список вершин
    }

    // 4. Поиск вершин с количеством входящих рёбер больше заданного числа.
    public List<Integer> getVerticesMore(int x) { // x - порог количества входящих рёбер
        Map<Integer, Integer> incomingEdgesCount = new HashMap<>(); // Хранит количество входящих рёбер для каждой вершины

        // Подсчитываем входящие рёбра для каждой вершины
        for (Edge edge : edges) { // Проходимся по всем ребрам
            incomingEdgesCount.put(edge.getTo(), incomingEdgesCount.getOrDefault(edge.getTo(), 0) + 1);
        }

        // Отбираем вершины, у которых количество входящих рёбер больше порога
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : incomingEdgesCount.entrySet()) {
            if (entry.getValue() > x) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    // 7. Удаление вершин с минимальным количеством входящих рёбер (без учёта петель).
    public void removeVerticesWithMinIncomingEdges() {
        Map<Integer, Integer> incomingEdgesCount = new HashMap<>(); // Храним количество входящих рёбер для каждой вершины

        // Подсчитываем входящие рёбра (исключая петли)
        for (Edge edge : edges) {
            if (edge.getFrom() != edge.getTo()) { // Исключаем петли
                incomingEdgesCount.put(edge.getTo(), incomingEdgesCount.getOrDefault(edge.getTo(), 0) + 1);
            }
        }
        if (incomingEdgesCount.isEmpty()) return; // Проверим наличие ребер, чтобы избежать ошибок

        // Находим минимальное количество входящих рёбер
        int minIncoming = incomingEdgesCount.values().stream().min(Integer::compareTo).orElse(0);

        // Удаляем вершины с минимальным количеством входящих рёбер
        Set<Integer> verticesToRemove = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : incomingEdgesCount.entrySet()) {
            if (entry.getValue() == minIncoming) {
                verticesToRemove.add(entry.getKey());
            }
        }

        // Удаляем найденные вершины и их рёбра
        for (int vertex : verticesToRemove) {
            removeVertex(vertex);
        }
    }

    // 13. Проверка на полноту графа
     public boolean isComplete() {
        int n = vertices.size(); // Количество вершин
        int expectedEdges = n * (n - 1); // Ожидаемое количество рёбер в полном графе (без петель)
        return edges.size() == expectedEdges; // Сравниваем текущее количество рёбер с ожидаемым
    }

    // 14. Проверка на пустоту графа
    public boolean isEmpty() {
        return edges.isEmpty(); // Если список рёбер пуст, граф пустой
    }

    // Получение множества всех вершин графа
    public Set<Integer> getVertices() {
        return new HashSet<>(vertices);
    }

    // Построение списка смежности
    public Map<Integer, Set<Integer>> buildAdjacencyList() {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        for (int vertex : vertices) {
            adjacencyList.put(vertex, new HashSet<>());
        }
        for (Edge edge : edges) {
            adjacencyList.get(edge.getFrom()).add(edge.getTo());
        }
        return adjacencyList;
    }
    // Сложение двух графов
    public Graph mergeGraphs(Graph other) { // other - другой граф для объединения
        Graph mergedGraph = new Graph(); // Создаём новый граф для результата
        mergedGraph.vertices.addAll(this.vertices); // Добавляем вершины из текущего графа
        mergedGraph.vertices.addAll(other.vertices); // Добавляем вершины из другого графа
        mergedGraph.edges.addAll(this.edges); // Добавляем рёбра из текущего графа
        mergedGraph.edges.addAll(other.edges);// Добавляем рёбра из другого графа

        return mergedGraph; // Возвращаем новый граф, содержащий объединение текущего и другого графов
    }

}
