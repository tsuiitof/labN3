class Graph {
    private int[][] edges; // массив рёбер
    private int edgeCount; // текущее количество рёбер

    private int[] vertices; // массив вершин
    private int vertexCount; // текущее количество вершин

    public Graph(int maxEdges, int maxVertices) {
        edges = new int[maxEdges][2];
        vertices = new int[maxVertices];
        edgeCount = 0;
        vertexCount = 0;
    }

    private boolean edgeExists(int v1, int v2) {
        for (int i = 0; i < edgeCount; i++) { // перебираем все добавленные ребра
            if (edges[i][0] == v1 && edges[i][1] == v2) // если нашли совпадение
                return true; // такое ребро уже есть
        }
        return false; // ребро не найдено
    }

    private boolean vertexExists(int v) {
        for (int i = 0; i < vertexCount; i++) {  // перебираем все вершины
            if (vertices[i] == v) // усли нашли вершину v
                return true; // она уже есть
        }
        return false; // вершина не найдена
    }

    private void addVertex(int v) {
        if (!vertexExists(v)) { // проверяем, есть ли уже такая вершинв
            if (vertexCount < vertices.length) { // проверяем, есть ли еще место в коде
                vertices[vertexCount] = v; // записываем вершину в массив
                vertexCount++; // увеличиваем счётчик после добавления
            } else
                System.out.println("Превышен лимит вершин!");
        }
    }

    public void addEdge(int v1, int v2) {
        if (edgeExists(v1, v2)) { // проверяем, есть ли уже такое ребро
            System.out.println("Ребро " + v1 + " -> " + v2 + " уже существует!");
            return;
        }

        if (edgeCount < edges.length) { // проверяем, есть ли место в массиве ребер
            edges[edgeCount][0] = v1; // записываем первую вершину в массив
            edges[edgeCount][1] = v2; // записываем вторую вершину
            edgeCount++;
            addVertex(v1); // добавляем v1 в список вершин
            addVertex(v2); // добавляем v2 в список вершин
        } else {
            System.out.println("Достигнуто максимальное число рёбер!");
        }
    }

    public void printEdges() {
        System.out.println("Список рёбер:");
        for (int i = 0; i < edgeCount; i++) // цикл проходится по всем ребрам, пока есть добавленные ребра
            System.out.println(edges[i][0] + " -> " + edges[i][1]);
    }

    public void printVerticesDescending() {
        // создаём новый массив и копируем вершины
        int[] sortedVertices = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++)
            sortedVertices[i] = vertices[i]; // заполняем новый массив значениями из старого

        // сортируем массив по убыванию (пузырьковая сортировка)
        for (int i = 0; i < vertexCount - 1; i++) {
            for (int j = 0; j < vertexCount - i - 1; j++) {
                if (sortedVertices[j] < sortedVertices[j + 1]) { // сравниваем текущий элемент и следующий
                    int temp = sortedVertices[j];
                    sortedVertices[j] = sortedVertices[j + 1];
                    sortedVertices[j + 1] = temp;
                }
            }
        }
        // Выводим отсортированные вершины
        System.out.println("Вершины по убыванию:");
        for (int i = 0; i < vertexCount; i++)
            System.out.print(sortedVertices[i] + " ");
        System.out.println();
    }

    public void printVerticesWithIncomingEdgesMoreThan(int count) {
        System.out.println("Вершины с входящими рёбрами больше " + count + ":");

        for (int i = 0; i < vertexCount; i++) { // перебираем вершины
            int v = vertices[i];
            int incoming = 0; // создаем счетчик входящих ребер

            for (int j = 0; j < edgeCount; j++) { // подсчёт входящих рёбер
                if (edges[j][1] == v) // проверяем, является ли v конечной точкой ребра.
                    incoming++;
            }
            if (incoming > count) { // проверка на превышение
                System.out.print(v + " ");
            }
        }
        System.out.println();
    }

    public void removeEdge(int v1, int v2) {
        if (edgeCount == 0) { // проверка на пустой граф
            System.out.println("Граф не содержит рёбер.");
            return;
        }
        for (int i = 0; i < edgeCount; i++) { // проходим по всем ребрам
            if (edges[i][0] == v1 && edges[i][1] == v2) {
                edges[i] = edges[edgeCount - 1]; // заменяем удаляемое ребро последним
                edgeCount--; // уменьшаем кол-во ребер
                System.out.println("Ребро " + v1 + " -> " + v2 + " удалено.");
                return;
            }
        }
        System.out.println("Ребро " + v1 + " -> " + v2 + " не найдено.");
    }

    public void removeVertex(int v) {
        // удаление рёбер, связанных с v
        int index = 0;
        for (int i = 0; i < edgeCount; i++) {
            if (edges[i][0] != v && edges[i][1] != v) {
                edges[index] = edges[i];// сдвигаем рёбра в начало массива
                index++;
            }
        }
        edgeCount = index; // Обновляем количество рёбер

        // Удаление самой вершины
        index = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] != v) {
                vertices[index] = vertices[i]; // Сдвигаем вершины в начало массива
                index++;
            }
        }
        vertexCount = index; // Обновляем количество вершин

        System.out.println("Вершина " + v + " и её рёбра удалены.");
    }

    public void removeVerticesWithFewestIncomingEdges() {
        if (vertexCount == 0) return; // выходим, если вершин нет

        int[] incomingEdges = new int[vertexCount]; // массив для хранения входящих ребер для каждой вершины

        // подсчёт входящих рёбер (без учёта петель)
        for (int i = 0; i < edgeCount; i++) {
            if (edges[i][0] != edges[i][1]) { // исключаем петли
                for (int j = 0; j < vertexCount; j++) { // ищем индекс вершины
                    if (vertices[j] == edges[i][1]) { // Нашли вершину-получатель
                        incomingEdges[j]++;
                        break;
                    }
                }
            }
        }

        // находим минимальное количество входящих рёбер
        int minIncoming = incomingEdges[0]; // берём первое значение как стартовое
        for (int i = 1; i < vertexCount; i++) {
            if (incomingEdges[i] < minIncoming) {
                minIncoming = incomingEdges[i];
            }
        }

        // удаляем вершины с минимальным числом входящих рёбер
        for (int i = 0; i < vertexCount; i++) {
            if (incomingEdges[i] == minIncoming) {
                removeVertex(vertices[i]);
                i--; // корректируем индекс после удаления
            }
        }
    }

    public int countConnectedComponents() {
        boolean[] visited = new boolean[vertexCount]; // массив для отслеживания посещенных вершин
        int count = 0;

        for (int i = 0; i < vertexCount; i++) {
            if (!visited[i]) { // если вершина не посещена
                dfs(i, visited); // запускаем обход в глубину
                count++;
            }
        }
        System.out.println("Количество компонент связности: " + count);
        return count;
    }

    private void dfs(int vIndex, boolean[] visited) {
        visited[vIndex] = true; // помечаем текущую вершину как посещенную
        for (int i = 0; i < edgeCount; i++) {
            if (edges[i][0] == vertices[vIndex] && !visited[getVertexIndex(edges[i][1])]) { // если вершина совпадает с текущейи  она не посещена
                dfs(getVertexIndex(edges[i][1]), visited); // вызываем dfs для соседней вершины
            }
            if (edges[i][1] == vertices[vIndex] && !visited[getVertexIndex(edges[i][0])]) {
                dfs(getVertexIndex(edges[i][0]), visited); // то же самого для второго конца ребра
            }
        }
    }

    private int getVertexIndex(int v) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == v) // если нашли нужную вершину
                return i; // возвращаем ее индекс
        }
        return -1; // если вершина не найдена
    }


    public int[][] findConnectedComponents() {
        boolean[] visited = new boolean[vertexCount]; // создаем массив для отслеживания посещенных вершин
        int componentCount = countConnectedComponents(); // количество компонент
        int[][] result = new int[componentCount][]; // массив для хранения компонент
        int componentIndex = 0; // используем для отслеживания номера текущей компоненты

        System.out.println("Компоненты связности:");

        for (int i = 0; i < vertexCount; i++) {
            if (!visited[i]) { // если вершина не была посещена
                int[] component = new int[vertexCount]; // максимальный возможный размер
                int size = 0; // счётчик количества вершин в текущей компоненте

                // запускаем dfs для сбора компонент
                size = dfsCollect(i, visited, component, size);

                int[] finalComponent = new int[size]; // создаем массив нужного размера
                for (int j = 0; j < size; j++)
                    finalComponent[j] = component[j];


                result[componentIndex] = finalComponent;
                componentIndex++; // уведичиваем индекс для следующей компоненты и храним все найденные компоненты

                // Выводим найденную компоненту связности
                System.out.print("{ ");
                for (int j = 0; j < size; j++)
                    System.out.print(finalComponent[j] + " ");

                System.out.println("}");
            }
        }

        return result;
    }

    private int dfsCollect(int vIndex, boolean[] visited, int[] component, int size) {
        visited[vIndex] = true; // помечаем вершину как посещенную
        component[size++] = vertices[vIndex]; // добавляем вершину в компоненту

        for (int i = 0; i < edgeCount; i++) {
            int neighborIndex = -1; // храним индекс соседней вершины
            if (edges[i][0] == vertices[vIndex]) // если первая вершина в ребре совпадает с текущей
                neighborIndex = getVertexIndex(edges[i][1]); // получаем индекс его соседа - edges[i][1]
            else if (edges[i][1] == vertices[vIndex]) // если вторая вершина в ребре совпадает с текущей
                neighborIndex = getVertexIndex(edges[i][0]); // значит получаем индекс его соседа - edges[i][0]


            if (neighborIndex != -1 && !visited[neighborIndex])  // если индекс вершины корректный и вершина еще не была посещена
                size = dfsCollect(neighborIndex, visited, component, size); // рекурсивно вызываем dfsCollect() для соседней вершины

        }
        return size; // возвращаем общее число вершин в текущей компоненте
    }

    public void printVerticesReachableInTwoSteps(int startVertex) {
        int[] reachable = new int[vertexCount]; // массив достижимых вершин
        boolean[] visited = new boolean[vertexCount]; // массив посещённых вершин
        int count = 0; // счётчик достижимых вершин

        // находим соседей переданной вершины (вершины, достижимые за 1 ход)
        for (int i = 0; i < edgeCount; i++) {
            if (edges[i][0] == startVertex && !visited[getVertexIndex(edges[i][1])]) { // если startVertex совпадает с edges[i][0], значит edges[i][1] — его сосед
                visited[getVertexIndex(edges[i][1])] = true; // помечаем вершину как посещенную
                reachable[count] = edges[i][1]; // добавляем в новый массив
                count++;
            } else if (edges[i][1] == startVertex && !visited[getVertexIndex(edges[i][0])]) { // если startVertex совпадает с edges[i][1], значит edges[i][0] — его сосед
                visited[getVertexIndex(edges[i][0])] = true;
                reachable[count] = edges[i][0]; // добавляем в тот же массив
                count++;
            }
        }

        // затем ищем соседей уже найденных вершин (достижимые за 2 хода)
        int currentCount = count; // сохраняем текущее количество вершин первого шага
        for (int i = 0; i < currentCount; i++) { // цикл идет только по вершинам, найденным на первом шаге
            int vertex = reachable[i]; // получаем i-ю вершину из reachable[]
            for (int j = 0; j < edgeCount; j++) {
                if (edges[j][0] == vertex && !visited[getVertexIndex(edges[j][1])]) { // проверяем, начинается ли текущее ребро из vertex и, Если второй конец ребра ещё не был найден ранее, добавляем его в reachable[]
                    visited[getVertexIndex(edges[j][1])] = true; // помечаем вершину как посещенную
                    reachable[count] = edges[j][1]; // добавляем ее в список достижимых
                    count++;
                } else if (edges[j][1] == vertex && !visited[getVertexIndex(edges[j][0])]) { // аналогичная проверка для второго конца ребра
                    visited[getVertexIndex(edges[j][0])] = true;
                    reachable[count] = edges[j][0];
                    count++;
                }
            }
        }

        // выводим результат
        System.out.print("Вершины, достижимые из " + startVertex + " за 2 хода: ");
        for (int i = 0; i < count; i++)
            System.out.print(reachable[i] + " ");

        System.out.println();
    }

    public void printVerticesReachableInSteps(int startVertex, int maxSteps) {
        boolean[] visited = new boolean[vertexCount]; // массив посещённых вершин
        int[] reachable = new int[vertexCount]; // массив достижимых вершин
        int count = 0; // счётчик достижимых вершин

        visited[getVertexIndex(startVertex)] = true; // отмечаем стартовую вершину как посещённую

        int[] currentLevel = new int[vertexCount]; // вершины текущего уровня (текущего хода)
        int[] nextLevel = new int[vertexCount]; // вершины следующего уровня
        int currentCount = 0, nextCount = 0;

        // начинаем поиск с соседей startVertex
        for (int i = 0; i < edgeCount; i++) {
            if (edges[i][0] == startVertex && !visited[getVertexIndex(edges[i][1])]) { // если startVertex является первой вершиной текущего ребра и вторая вершина еще не была посещена
                visited[getVertexIndex(edges[i][1])] = true; // помечаем вершину как посещенную
                currentLevel[currentCount++] = edges[i][1]; // добавляем найденную вершину в массив текущего уровня, чтобы затем искать её соседей
                reachable[count++] = edges[i][1]; // добавляем вершину в список всех достижимых вершин
            } else if (edges[i][1] == startVertex && !visited[getVertexIndex(edges[i][0])]) { // аналогично проверяем для случая где startVertex явл. второй вершиной ребра
                visited[getVertexIndex(edges[i][0])] = true;
                currentLevel[currentCount++] = edges[i][0];
                reachable[count++] = edges[i][0];
            }
        }

        // ищем вершины на следующих уровнях (до maxSteps шагов)
        for (int step = 1; step < maxSteps; step++) {
            nextCount = 0; // кол-во вершин след. уровня
            for (int i = 0; i < currentCount; i++) { // перебор всех вершин, найденных на предыдущем шаге
                int vertex = currentLevel[i];
                for (int j = 0; j < edgeCount; j++) { // перебираем ребра
                    if (edges[j][0] == vertex && !visited[getVertexIndex(edges[j][1])]) {
                        visited[getVertexIndex(edges[j][1])] = true;
                        nextLevel[nextCount++] = edges[j][1]; // добавляем посещенную вершину в nextLevel, так как она будет участвовать в следующем шаге
                        reachable[count++] = edges[j][1]; // добавляем в reachable, так как она достижима в ≤ maxSteps ходов
                    } else if (edges[j][1] == vertex && !visited[getVertexIndex(edges[j][0])]) {
                        visited[getVertexIndex(edges[j][0])] = true;
                        nextLevel[nextCount++] = edges[j][0];
                        reachable[count++] = edges[j][0];
                    }
                }
            }

            // меняем текущий уровень на следующий
            int[] temp = currentLevel;
            currentLevel = nextLevel;
            nextLevel = temp;
            currentCount = nextCount;
        }

        // Вывод результата
        System.out.print("Вершины, достижимые из " + startVertex + " за не более чем " + maxSteps + " ходов: ");
        for (int i = 0; i < count; i++)
            System.out.print(reachable[i] + " ");

        System.out.println();
    }

    public void mergeGraph(Graph other) {
        // добавляем вершины из второго графа, если их нет в первом графе
        for (int i = 0; i < other.vertexCount; i++) { // перебираем все вершины во 2 графе
            if (!containsVertex(other.vertices[i])) { // проверяем, есть ли эта вершина в текущем графе
                vertices[vertexCount] = other.vertices[i]; // добавляем вершину в массив
                vertexCount++; // увеличиваем счетчик
            }
        }

        // Добавляем рёбра из второго графа, если их нет в первом графе
        for (int i = 0; i < other.edgeCount; i++) { // перебор ребер из второго графа
            int v1 = other.edges[i][0];
            int v2 = other.edges[i][1]; // берем 1 и 2 вершины текущего ребра
            if (!containsEdge(v1, v2)) { // проверяем, есть ли уже такое ребро в текущем графе (this)
                edges[edgeCount][0] = v1; // записываем первую вершину нового ребра в массив edges
                edges[edgeCount][1] = v2; // записываем вторую вершину нового ребра
                edgeCount++; // увеличиваем счетчик ребер
            }
        }
    }

    // проверяем, содержится ли вершина в текущем графе
    private boolean containsVertex(int vertex) {
        for (int i = 0; i < vertexCount; i++) { // перебираем веершины
            if (vertices[i] == vertex)
                return true;
        }
        return false;
    }

    // проверяем, существует ли уже такое ребро в текущем графе
    private boolean containsEdge(int v1, int v2) {
        for (int i = 0; i < edgeCount; i++) {
            if ((edges[i][0] == v1 && edges[i][1] == v2)) { // проверяем оба направления
                return true;
            }
        }
        return false;
    }

    public boolean isCompleteGraph() {
        if (vertexCount < 2) {
            System.out.println("Граф полный? true (0 или 1 вершина)");
        return true; // граф с 0 или 1 вершиной считается полным
    }
        int maxEdges = (vertexCount * (vertexCount - 1)) / 2; // максимальное число рёбер в полном графе
        boolean isComplete = edgeCount == maxEdges;

        System.out.println("Граф полный? " + isComplete);
        return isComplete;
    }

    public boolean isEmptyGraph() {
        boolean isEmpty = (vertexCount == 0 || edgeCount == 0); // граф пустой, если нет вершин или нет рёбер

        System.out.println("Граф пустой? " + isEmpty);
        return isEmpty;
    }

    public boolean hasOnlySelfLoops() {
        boolean[] hasEdge = new boolean[vertexCount]; // массив для отметки вершин, имеющих связи с другими

        for (int i = 0; i < edgeCount; i++) {
            int v1 = edges[i][0];
            int v2 = edges[i][1];

            if (v1 != v2) { // если есть хотя бы одно ребро, не являющееся петлёй
                hasEdge[getVertexIndex(v1)] = true;
                hasEdge[getVertexIndex(v2)] = true;
            }
        }

        // проверяем, есть ли вершина, у которой нет внешних связей
        boolean found = false;
        for (int i = 0; i < vertexCount; i++) {
            if (!hasEdge[i]) { // вершина не имеет внешних рёбер (либо только петли, либо вообще нет рёбер)
                found = true;
                break;
            }
        }

        System.out.println("Есть ли вершины, соединённые только с собой? " + found);
        return found;
    }
}
