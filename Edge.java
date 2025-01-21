import java.util.Objects;
public class Edge {
    private final int from; // Начальная вершина ребра
    private final int to;   // Конечная вершина ребра

    public Edge(int from, int to) { // Конструктор для создания объекта Edge
        this.from = from;
        this.to = to;
    }

    // Геттер для начальной вершины
    public int getFrom() {
        return from;
    }

    // Геттер для конечной вершины
    public int getTo() {
        return to;
    }

    // Переопределение метода toString() для удобного вывода информации о ребре.
    @Override
    public String toString() {
        return "(" + from + " -> " + to + ")";
    }

    // Переопределение метода equals() для сравнения рёбер.
    @Override
    public boolean equals(Object x) {
        if (this == x) return true; // Если объекты идентичны
        if (x == null || getClass() != x.getClass()) return false; // Если типы объектов различаются
        Edge edge = (Edge) x;
        // Сравниваем начальную и конечную вершины
        return from == edge.from && to == edge.to;
    }

    // Переопределение метода hashCode() для использования объекта Edge
    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
