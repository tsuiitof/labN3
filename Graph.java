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

    // 3. Вывод списка вершин графа, отсортированного по убыванию.
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
        for (Map.Entry<Integer, Integer> entry : incomingEdgesCount.entrySet()) { // Проходимся по всем записям мапы
            if (entry.getValue() > x) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
    // 5. Удаление указанного ребра из графа.
    public void removeEdge(int from, int to) {
        Edge edgeToRemove = new Edge(from, to); // Создаём объект ребра для удаления
        edges.remove(edgeToRemove); // Удаляем ребро из списка (если оно существует)
    }

    // 6. Удаление вершины из графа, включая все её входящие и исходящие рёбра.
    public void removeVertex(int vertex) { // vertex - вершина для удаления
        // Удаляем рёбра, связанные с данной вершиной
        Iterator<Edge> iterator = edges.iterator(); // Создаем итератор для обхода коллекции edges
        while (iterator.hasNext()) { // Цикл проходится по всем ребрам графа
            Edge edge = iterator.next(); // Получаем текущее ребро
            if (edge.getFrom() == vertex || edge.getTo() == vertex) { // Проверяем, связано ли текущее ребро с вершиной, которую нужно удалить
                iterator.remove(); // Удаляем текущее ребро, если оно связано с удаляемой вершиной
            }
        }
        // Удаляем указанную вершину
        vertices.remove(vertex);
    }

    // 7. Удаление вершин с минимальным количеством входящих рёбер (без учёта петель).
    public void removeVerticesWithMinIncomingEdges() {
        Map<Integer, Integer> incomingEdgesCount = new HashMap<>(); // Храним количество входящих рёбер для каждой вершины

        // Подсчитываем входящие рёбра (исключая петли)
        for (Edge edge : edges) { // Проход по всем ребрам
            if (edge.getFrom() != edge.getTo()) { // Исключаем петли
                incomingEdgesCount.put(edge.getTo(), incomingEdgesCount.getOrDefault(edge.getTo(), 0) + 1);
            }
        }
        if (incomingEdgesCount.isEmpty()) return; // Проверим наличие ребер, чтобы избежать ошибок

        // Находим минимальное количество входящих рёбер
        int minIncoming = incomingEdgesCount.values().stream().min(Integer::compareTo).orElse(0);

        // Удаляем вершины с минимальным количеством входящих рёбер
        Set<Integer> verticesToRemove = new HashSet<>(); // Создаем множество вершин, которые нужно удалить
        for (Map.Entry<Integer, Integer> entry : incomingEdgesCount.entrySet()) { // Обходим все пары (вершина, кол-во входящих ребер)
            if (entry.getValue() == minIncoming) { // Если кол-во входящих ребер вершины равно минималньому значению
                verticesToRemove.add(entry.getKey()); // Добавляем эту вершину в созданное множество
            }
        }
        // Удаляем найденные вершины и их рёбра
        for (int vertex : verticesToRemove) { // Проходимя по всем
            removeVertex(vertex);
        }
    }

    // Построение списка смежности
    public Map<Integer, Set<Integer>> buildAdjacencyList() {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>(); // Создаем пустой список смежности
        for (int vertex : vertices) { // Проходимся по всем вершинам графа
            adjacencyList.put(vertex, new HashSet<>()); // Для каждой вершины добавляем пустое множество
        }
        for (Edge edge : edges) { // Проходимся по всем ребрам графа
            adjacencyList.get(edge.getFrom()).add(edge.getTo()); // Находим множество соседей начальной вершины и добавляем туда конечную вершину
        }
        return adjacencyList; // Возвращаем построенный список смежности
    }

    // 8. Подсчёт количества компонент связности в графе
    public int countConnectedComponents(Graph graph) {
        return getConnectedComponents(graph).size();
    }

    // 9. Поиск компонент связности
    public List<List<Integer>> getConnectedComponents(Graph graph) {
        Map<Integer, Set<Integer>> adjacencyList = buildAdjacencyList(); // Строим список смежности
        Set<Integer> visited = new HashSet<>(); // Множество для отслеживания посещенных вершин
        List<List<Integer>> components = new ArrayList<>(); // Список для хранения компонент связности

        // Проходим по всем вершинам графа
        for (int vertex : graph.getVertices()) {
            if (!visited.contains(vertex)) { // Если вершина ещё не посещена
                List<Integer> component = new ArrayList<>(); // Текущая компонента связности
                dfs(vertex, adjacencyList, visited, component); // Исследуем компоненту с помощью DFS
                components.add(component); // Добавляем компоненту в список
            }
        }
        return components; // Возвращаем все компоненты связности
    }

    // Вспомогательный метод для выполнения DFS (обход графа в глубину). Заполняет множество компонент для текущей компоненты связности
    private void dfs(int vertex, Map<Integer, Set<Integer>> adjacencyList, Set<Integer> visited, List<Integer> component) {
        visited.add(vertex); // Помечаем текущую вершину как посещённую
        component.add(vertex); // Добавляем её в текущую компоненту

        // Рекурсивно посещаем всех соседей вершины
        for (int neighbor : adjacencyList.getOrDefault(vertex, new HashSet<>())) {
            if (!visited.contains(neighbor)) { // Если сосед еще не посещен
                dfs(neighbor, adjacencyList, visited, component);
            }
        }
    }

    // 10. Метод, который возвращает вершины, достижимые за не более чем 2 хода.
    public static Set<Integer> getReachableVerticesIn2Moves(Graph graph, int start) { // start - начальная вершина

        Map<Integer, Set<Integer>> adjacencyList = graph.buildAdjacencyList(); // Получаем список смежности из графа
        Set<Integer> reachableVertices = new HashSet<>(); // Множество для хранения достижимых вершин
        Set<Integer> firstStepVertices = adjacencyList.getOrDefault(start, new HashSet<>()); // Множество для хранения вершин, достижимых за 1 шаг (соседи)
        reachableVertices.addAll(firstStepVertices); // Добавляем все вершины, достижимые за 1 шаг
        Set<Integer> secondStepVertices = new HashSet<>(); // Теперь добавляем вершины, достижимые за 2 шага

        for (int vertex : firstStepVertices) { // Итерация по вершинам, найденным на 1-м шаге
            secondStepVertices.addAll(adjacencyList.getOrDefault(vertex, new HashSet<>()));
        }
        reachableVertices.addAll(secondStepVertices); // Добавляем в результат вершины, достижимые за 2 шага
        reachableVertices.remove(start); // Убираем начальную вершину из результатов (если она есть)

        return reachableVertices;
    }

    // 11. Поиск вершин, достижимых за заданное количество шагов
    public static Set<Integer> getVerticesReachableInKSteps(Graph graph, int start, int steps) {
        Set<Integer> reachable = new HashSet<>();
        Set<Integer> current = new HashSet<>();
        current.add(start);

        for (int i = 0; i < steps; i++) {
            Set<Integer> next = new HashSet<>();
            for (int vertex : current) {
                if (graph.buildAdjacencyList().containsKey(vertex)) {
                    next.addAll(graph.buildAdjacencyList().get(vertex));
                }
            }
            reachable.addAll(next);
            current = next;
        }
        return reachable;
    }

    // 12. Сложение двух графов
    public void sumGraphs(Graph other) { // other - другой граф для объединения
        Graph sumGraph = new Graph(); // Создаём новый граф для результата
        sumGraph.vertices.addAll(this.vertices); // Добавляем вершины из текущего графа
        sumGraph.vertices.addAll(other.vertices); // Добавляем вершины из другого графа
        sumGraph.edges.addAll(this.edges); // Добавляем рёбра из текущего графа
        sumGraph.edges.addAll(other.edges);// Добавляем рёбра из другого графа

    }

    // 13. Проверка на полноту графа
    public boolean isComplete() {
        int n = vertices.size(); // Количество вершин
        int expectedEdges = (n * (n - 1)) ; // Ожидаемое количество рёбер в полном графе (без петель)
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

    // 15. Проверяем, есть ли в графе вершины, которые соединены только с собой (имеют только петли).
    public boolean hasVerticesWithOnly(Map<Integer, Set<Integer>> adjacencyList) { // adjacencyList - список смежности графа
        // Проходим по каждой вершине в списке смежности
        for (Map.Entry<Integer, Set<Integer>> entry : adjacencyList.entrySet()) { // Итерация по каждой паре (Map.Entry) из списка смежности
            int vertex = entry.getKey(); // Получаем ключ текущей пары, который представляет вершину графа (vertex)
            Set<Integer> neighbors = entry.getValue(); // Получаем значение текущей пары, которое представляет множество соседей вершины (neighbors)

            // Условие: если у вершины есть только одно ребро, и это ребро — петля (ведет обратно к самой вершине)
            if (neighbors.size() == 1 && neighbors.contains(vertex)) {
                return true; // Найдена хотя бы одна вершина, соединённая только с собой
            }
        }
        // Если не нашли таких вершин, возвращаем false
        return false;
    }
}
