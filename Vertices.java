import java.util.*;
public class Vertices {

    // Подсчёт количества компонент связности в графе
    public static int countConnectedComponents(Graph graph) {
        List<Set<Integer>> connectedComponents = getConnectedComponents(graph);
        return connectedComponents.size();
    }

    // Поиск компонент связности
    public static List<Set<Integer>> getConnectedComponents(Graph graph) {
        Map<Integer, Set<Integer>> adjacencyList = graph.buildAdjacencyList();
        Set<Integer> visited = new HashSet<>();
        List<Set<Integer>> components = new ArrayList<>();

        // Проходим по всем вершинам графа
        for (int vertex : graph.getVertices()) {
            if (!visited.contains(vertex)) {
                Set<Integer> component = new HashSet<>();
                dfs(vertex, adjacencyList, visited, component); // Выполняем DFS
                components.add(component);
            }
        }
        return components;
    }

    // Вспомогательный метод для выполнения DFS (обход графа в глубину). Заполняет множество компонент для текущей компоненты связности
    private static void dfs(int vertex, Map<Integer, Set<Integer>> adjacencyList, Set<Integer> visited, Set<Integer> component) {
        visited.add(vertex); // Помечаем текущую вершину как посещённую
        component.add(vertex); // Добавляем её в текущую компоненту

        // Рекурсивно посещаем всех соседей вершины
        for (int neighbor : adjacencyList.getOrDefault(vertex, new HashSet<>())) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, adjacencyList, visited, component);
            }
        }
    }

    // Поиск вершин, достижимых за заданное количество шагов
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

    // Метод, который возвращает вершины, достижимые за не более чем 2 хода.
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
    // Проверяем, есть ли в графе вершины, которые соединены только с собой (имеют только петли).
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

