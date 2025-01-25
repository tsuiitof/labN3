# LabN3
## Отчет по лабораторной работе № 3

#### № группы: `ПМ-2402`

#### Выполнил: `Соколовская Снежана Владимировна`

#### Вариант: `22`

### Cодержание:

- [Постановка задачи](#1-постановка-задачи)
- [Классы](#2-классы)
- [Описание](#3-описание)

### 1. Постановка задачи

- Условия задачи
 > Передо мной стояла задача разработать программу для работы с графами, представленными в виде набора рёбер, а также реализовать функции для анализа структуры графа, добавления и удаления рёбер и вершин, определения свойств графа и выполнения операций над ним.

### 2. Классы

Мы знаем, что каждый класс отвечает за конкретную задачу, разделяя большую программу на несколько маленьких. Я сделала также, разделив программу на четыре "части". Так, например, класс `Edge` отвечает за структуру ребра, класс `Graph` хранит вершины, рёбра и выполняет основные операции (добавление/удаление), а класс `ShowGraph`  отвечает за отображение информации о графе. Последним является главный класс `Main` для запуска программы, одновременно являющийся тестовой программой.

1. Класс `Edge`:
Класс Edge представляет одно ребро в графе. Ребро связывает две вершины, и каждая вершина может быть связана с другой вершиной. Ребро хранит информацию о вершинах, между которыми оно проходит (от и до).
2. Класс `Graph`:
Класс Graph представляет граф с вершинами и рёбрами. Он отвечает за хранение данных о графе и выполнение различных операций с графом. В нем хранится множество вершин (vertices),  список рёбер (edges) и флаг directed
3. Класс `ShowGraph`:
Этот класс принимает объект графа (`Graph`) в качестве входного параметра через конструктор и предоставляет методы для визуализации и анализа структуры графа.

### 3. Описание

В этот раз мы учтем тестовый класс `Main`.

**1. Класс `Edge`:**

В контексте графов, ребро является фундаментальной структурой, которая связывает две вершины, и класс Edge служит для представления этой связи.
Сначала мы создаем два поля, которые хранят информацию о начальной и конечной вершине ребра - from и to.
Я сделала вершины final, чтобы их значения нельзя было изменить после инициализации, так как это важно для сохранения целостности данных о ребре.
Далее конструктор присваивает значения параметров полям объекта, что позволяет создать новый объект Edge, представляющий связь между двумя вершинами графа.Конструктор инициализирует объект рёбра с заданными вершинами from и to.
Я также добавила геттеры для доступа к вершинам ребра. Это позволяет другим классам получать информацию о ребре, не имея возможности напрямую изменять его состояние.
Далее я переопределяю метод equals, что позволяет корректно сравнивать два объекта Edge. Этим методом я проверяю, является ли объект, с которым происходит сравнение, экземпляром класса Edge. Затем мы сравниваем начальную и конечную вершины рёбер. Если обе вершины совпадают (т.е. у двух рёбер одинаковые from и to), то рёбра считаются равными. Это важно для корректного добавления рёбер в коллекции, такие как HashSet, где рёбра должны быть уникальными.
Переопределение метода hashCode позволяет корректно вычислять хеш-код объекта Edge, что важно при использовании рёбер в коллекциях, таких как HashSet или HashMap. Хеш-код рёбер рассчитывается на основе их начальной и конечной вершины. Это гарантирует, что два рёбра с одинаковыми вершинами будут иметь одинаковый хеш-код, что важно для корректного функционирования хеш-коллекций. В этом методе я использую стандартный метод Objects.hash для вычисления хеш-кода на основе полей from и to. Это упрощает код и минимизирует вероятность ошибок при реализации вычисления хеш-кода вручную.
Кроме того, я переопределила метод toString, чтобы предоставить удобное представление ребра в виде строки. Это позволяет легко выводить информацию о ребре, например, на экране. 

**2. Класс `Graph`:**

Поля этого класса хранят множество всех вершин графа и список всех рёбер графа. Здесь снова использую private final, чтобы доступ был ограничен классом Graph, а поле неизменно. Использую Set<Integer> для обеспечения уникальности вершин и List<Edge> для хранения порядка добавления рёбер.
Конструктор графа инициализирует поля vertices и edges пустыми коллекциями.

Метод `printEdges` возвращает все рёбра графа в порядке их добавления. Я добавляю этот метод для предоставления доступа к списку рёбер (edges), но при этом оригинальная коллекция защищена от изменений. Если метод просто возвращал бы ссылку на edges (например, return edges;), то внешний код мог бы напрямую видоизменять содержимое списка.

Метод `addEdge` добавляет новое ребро между вершинами from и to. Если ребра или вершины ещё нет, они добавляются. Сначала проверяем, существует ли уже такое ребро.Если такого ребра ещё нет, оно добавляется в коллекцию `edges`. Затем добавляем вершины, соответствующие началу и концу ребра, в коллекцию.

Метод `printVerticesDescending` возвращает список вершин графа, отсортированный по убыванию. По умолчанию метод `sort()` сортирует элементы по возрастанию, мы добавляем специальный компаратор, который сортирует элементы в обратном (или убывающем) порядке. И возвращаем уже отсортированный список вершин.

Метод `getVerticesMore(int x)` возвращает вершины, у которых количество входящих рёбер больше x (заданное значение). Сначала создаем мапу для подсчета входящих ребер, затем проходимся по каждому ребру графа, получаем текущее количество входящих рёбер для вершины `edge.getTo()`. Если вершина ещё не добавлена в мапу, возвращаем значение по умолчанию (`0`) и увеличиваем количество входящих рёбер на 1. Затем обновляем значение для вершины в мапе. На выходе `incomingEdgesCount` будет содержать количество входящих рёбер для каждой вершины графа. 
Создаю список для хранения вершин, у которых количество входящих рёбер превышает x. Проходимся по всем записям мапы. Если количество входящих рёбер больше порога x, добавляем вершину (`entry.getKey()`) в список `result`, который затем возвращаем.

Метод `removeEdge` удаляет ребро между вершинами from и to. Сначала создаю новый объект ребра для дальнейшего удаления и затем удаляю ребро из списка, если оно существует.

Метод `removeVertex` удаляет вершину и все рёбра, связанные с ней. Сначала я создаю итератор для обхода всех ребер, затем с помощью цикла while прохожусь по всем ребрам графа (метод `hasNext()` возвращает true, если в коллекции имеется ребро, иначе возвращает false). Далее с помощью цикла for проверяем, принадлежит ли указанное ребро начальной или конечной вершине (которые нужно удалить) и удаляем текущее ребро из коллекции, если оно связано с удаляемой вершиной (использую `iterator.remove()`, т.к. прямое удаление элементов из коллекции в цикле (`edges.remove(edge)`) вызывает `ConcurrentModificationException`, а использование итератора позволяет безопасно удалять элементы во время обхода). Потом удаляю указанную вершину из коллекции.

Метод `removeVerticesWithMinIncomingEdges` удаляет вершины с минимальным количеством входящих рёбер (без учёта петель). Сначала я снова создаю мапу для подсчёта входящих рёбер. Далее также, как в предыдущем методе прохожусь по всем ребрам, проверяю чтобы начальная и конечная вершина не совпадали (исключаю петли). Потом увеличиваем счётчик входящих рёбер для вершины `edge.getTo()`. Если вершина ещё не присутствует в мапе, её значение по умолчанию устанавливается равным `0`. Если мапа `incomingEdgesCount` пуста (то есть в графе нет рёбер, кроме петель), метод завершает выполнение. Далее находим минимальное количество входящих рёбер: с помощью `incomingEdgesCount.values()` извлекаем все значения (количество входящих рёбер) из мапы, находим минимальное значение среди всех количеств входящих рёбер. Если список значений пуст (на всякий случай), возвращаем 0.

Метод `buildAdjacencyList` отвечает за построение списка смежности. Сначлал создаю мапу с ключами - вершинами графа и значениями -  множества соседей каждой вершины. Далее прохожусь по списку всех вершин графа. Для каждой вершины добавляется запись в список смежности, где ключ — текущая вершина (`vertex`), а значение — пустое множество `HashSet`, которое будет хранить соседей этой вершины. Затем проходимся циклом по всем ребрам графа, находим множество соседей начальной вершины и добавляем туда конечную вершину. Возвращаем построенный список смежности.

Метод `getConnectedComponents` отвечает за поиск компонент связности. Сначала вызываем метод `buildAdjacencyList`, который строит представление графа в виде списка смежности. Затем создаем множество для отслеживания посещенных вершин.
components содержит все найденные компоненты связности. Каждая компонента представлена как список вершин. Затем проходимся по всем вершинам графа: если вершина ещё не посещена, это значит, что она принадлежит новой компоненте связности. Тогда создаем новый список component, который заполняется вершинами текущей компоненты связности. Для поиска всех связанных вершин вызывается метод `dfs`. 
Метод `dfs` (обход графа в глубину) рекурсивно посещает все вершины, которые связаны с данной вершиной, и добавляет их в текущую компоненту связности. Вершина `vertex` добавляется в множество `visited`, чтобы отметить её как обработанную. Вершина добавляется в текущую компоненту связности. Затем проходимся циклом по всем соседям вершины (полученным из списка смежности).
Если сосед ещё не посещён, то вызывается `dfs` для него, чтобы исследовать его связи.

Метод `getReachableVerticesIn2Moves` предназначен для нахождения всех вершин, достижимых за не более чем два хода от заданной начальной вершины в графе. Сначала я получаю список смежности из графа, затем создаю множество для хранения достижимых вершин и множество для хранения вершин, достижимых за 1 шаг (соседи). Эти вершины добавляются в множество `reachableVertices`. Затем для каждой вершины, достижимой за 1 шаг, мы ищем её соседей (т.е. вершины, которые достижимы за 2 шага от начальной). Эти вершины добавляются в `secondStepVertices`. Все вершины, найденные на втором шаге, добавляются в общее множество достижимых вершин. Исключается начальная вершина из множества, потому что нас интересуют только вершины, достижимые за 1 или 2 хода.

Метод `getVerticesReachableInKSteps` предназначен для поиска всех вершин, которые можно достичь за зада́нное количество шагов (`steps`) от начальной вершины `start` в графе. Здесь создаются два множества:
`reachable`: будет содержать все вершины, которые достижимы за указанное количество шагов.
current: хранит вершины, которые достижимы на текущем шаге. Сначала это только начальная вершина `start`, которую добавляем в current.
Для каждого шага (всего `steps` шагов) создаётся новое множество next, которое будет содержать вершины, достижимые в следующем шаге. Для каждой вершины в множестве `current` (вершины, достижимые на предыдущем шаге) мы ищем её соседей. Это делается через метод `buildAdjacencyList()`, который строит список смежности, и добавляем найденные вершины в множество next.
После того как все соседи для всех текущих вершин найдены, множество next добавляется в итоговое множество `reachable`, которое хранит все вершины, достижимые за указанное количество шагов.
Множество `current` обновляется и становится равным `next`, чтобы на следующем шаге рассматривать уже новые вершины. После завершения всех шагов возвращается множество `reachable`, которое содержит все вершины, достижимые за заданное количество шагов.

Метод `sumGraphs` объединяет два графа (суммирует вершины и рёбра без дублирования). Создаём новый граф для результата. В это множество добавляются все вершины из текущего графа (графа, для которого вызывается метод). Множество вершин из графа `other` (другого графа, переданного в метод) также добавляется в `sumGraph.vertices`. Таким образом, все вершины из обоих графов окажутся в результирующем графе. Аналогично добавляются рёбра из текущего графа (графа, для которого вызван метод) в новое множество рёбер `sumGraph.edges`. Рёбра из другого графа (`other.edges`) также добавляются в `sumGraph.edges`. Таким образом, рёбра из обоих графов будут объединены в результирующем графе.

Метод `isComplete` проверяет, является ли граф полным (каждая вершина соединена с каждой). Сначала вычисляется количество вершин в графе, используя метод `size()` множества `vertices`, которое хранит все вершины графа. Флаг `directed` — параметр, который сообщает, является ли граф направленным. Если это так, то для каждой пары вершин мы ожидаем два рёбра: одно от вершины A к вершине B и одно от B к A. Это даёт количество рёбер n×(n−1). Если граф ненаправленный, то для каждой пары вершин считается одно ребро, поэтому ожидаемое количество рёбер будет равно (n×(n−1))/2. И в итоге сравниваем текущее количество рёбер в графе (с помощью `edges.size()`) с рассчитанным ожидаемым количеством рёбер.

Метод `isEmpty` проверяет, пуст ли граф. Если список рёбер пуст, граф пустой. Метод возвращает true или false.

Метод `getVertices` возвращает множество всех вершин графа в виде нового объекта `HashSet`. Создаем новый объект `HashSet` и инициализируем его элементами из коллекции `vertices`. Это создаёт новое множество, которое содержит все вершины текущего графа. Важно! Если `vertices` — это множество, то `new HashSet<>(vertices)` просто копирует все элементы из оригинального множества в новый объект `HashSet`. Это важный момент, потому что возвращение самого множества `vertices` могло бы привести к изменению внутреннего состояния объекта графа. Здесь создаётся новый экземпляр, что позволяет избежать сторонних изменений.

Метод `hasVerticesWithOnly` проверяет, есть ли в графе вершины, которые соединены только с собой (имеют только петли).  Проходим по каждой вершине в списке смежности. Далее - итерация по каждой паре (`Map.Entry`) из списка смежности. Получаем ключ текущей пары, который представляет вершину графа (`vertex`). Получаем значение текущей пары, которое представляет множество соседей вершины (neighbors). Если у вершины есть только одно ребро, и это ребро — петля (ведет обратно к самой вершине) - значит найдена хотя бы одна вершина, соединённая только с собой, возвращает true, иначе - false


**3. Класс `ShowGraph`:**

Конструктор принимает объект типа `Graph` и сохраняет его в поле `graph` для последующего использования. 

Метод `displayEdges()` выводит на экран список всех рёбер графа. Используем цикл `for-each`, чтобы пройти по каждому элементу из коллекции рёбер, возвращаемой методом `graph.printEdges()`. Затем для каждого ребра (объекта `Edge`) выводится его строковое представление.

Метод `displayVerticesDescending()` выводит вершины графа в порядке убывания. Вызываем метод `printVerticesDescending()` у объекта graph.
Этот метод должен вернуть структуру данных (например, `List<Integer>` или `int[]`), содержащую вершины графа, отсортированные по убыванию. Используем цикл for для перебора всех вершин, возвращённых методом. Затем для каждой вершины выводится её значение.

Метод мgetVerticesMore(int x)` выводит вершины графа, у которых количество входящих рёбер больше, чем x. Вызывает метод `getVerticesMore(x)` у объекта `graph`, он возвращает коллекцию вершин больше x. Снова используем цикл `for`, чтобы перебрать каждую вершину.

Метод `getConnectedComponents()` выводит компоненты связности графа как список массивов. Вызываем метод: каждая компонента связности представлена как список вершин (`List<Integer>`); все компоненты связности объединены в общий список (`List<List<Integer>>`). Выводим список компонент связности целиком.

Метод `displayConnectedComponentsCount()` выводит количество компонент связности графа. Каждая компонента связности представлена в виде списка вершин (`List<Integer>`). Все такие списки объединены в один общий список. Метод `.size()` вызываем для списка компонент связности (`components`) и возвращаем количество таких компонент.

Метод `displayReachableInTwoSteps(int start)` выводит вершины, которые можно достичь из заданной вершины start за 2 хода. Мы проходимся по каждому числу (`vertex`) из списка вершин, который вернул метод `getReachableVerticesIn2Moves`.
Выводиь каждый номер вершины.

Метод `displayReachableInSteps(int start, int steps)` выводит вершины, достижимые из заданной вершины `start` за указанное количество ходов `steps`.  Мы также проходимся по каждому числу (`vertex`) из списка вершин, который вернул метод `getReachableVerticesIn2Moves` и выводим каждый номер вершины.

Метод `displayGraphProperties()` выводит свойства графа: полнота графа (все вершины соединены между собой); пустота графа (граф не содержит рёбер); наличие вершин, соединённых только с собой (петли). Сначала я вызываю метод `buildAdjacencyList()` у объекта graph, чтобы получить список смежности графа. Затем метод `isComplete()` у объекта graph для проверки, является ли граф полным, иметод `isEmpty()` у объекта graph для проверки, является ли граф пустым. А также метод `hasVerticesWithOnly(adjacencyList)` для проверки, есть ли в графе вершины, которые имеют только петли (ребра, ведущие к самим себе).


**4. Класс `Main`**

Как я уже упоминала, используется как тестовый. 
Создадим в нем граф и объект для отображения графа. 
Добавим данные с помощью метода addEdge();


1. showGraph.displayEdges(); - показываем граф

 **Output**:
      ```
Рёбра графа:
(1 -> 2)
(2 -> 3)
(1 -> 3)
(3 -> 1)
(4 -> 5)
(5 -> 6)
(6 -> 4)
```

2. showGraph.displayVerticesDescending(); - отображаем все вершины графа по убыванию

**Output**:
      ```
Вершины в порядке убывания:
6 5 4 3 2 1
```

3.  showGraph.displayReachableInTwoSteps(2); - отображаем вершины, достижимые за 2 хода из вершины k (здесь k задаем сами - целое число, в данном случае - 2)

**Output**:
      ```
Вершины, достижимые из 2 за 2 хода:
1 3 
```

4. showGraph.displayReachableInSteps(3, 5); - отображаем вершины, достижимые за n ходов из вершины k (здесь также сами задаем n и k, в данном случае - 3 и 5)

**Output**:
      ```
Вершины, достижимые из 3 за 5 ходов:
1 2 3 
```
5. showGraph.getVerticesMore(1); - отображение вершин с количеством входящих рёбер больше k (k задается пользователем, в данном случае - 1)

**Output**:
      ```
Вершины с количеством входящих рёбер больше 1:
3 

```

6.  graph.removeEdge(4, 5);
    showGraph.displayEdges();    -  удаляем ребро из графа (ребро 4 -> 5)

**Output**:
      ```
Рёбра графа:
(1 -> 2)
(2 -> 3)
(1 -> 3)
(3 -> 1)
(5 -> 6)
(6 -> 4)

```
7.  showGraph.displayConnectedComponentsCount(); - отображение количества компонент графа

**Output**:
      ```
Количество компонент связности: 3

```

8. showGraph.getConnectedComponents(); - отображение компонент связности в виде массива массивов

**Output**:
      ```
Компоненты связности:
[[1, 2, 3], [4], [5, 6]]

```
9. graph.removeVertex(3);
   showGraph.displayEdges();  - удаляем вершину (3) и повторно отображаем граф

  **Output**:
      ```
Рёбра графа:
(1 -> 2)
(5 -> 6)
(6 -> 4)

```  
10. graph.removeVerticesWithMinIncomingEdges();
    showGraph.displayEdges();    -  Удаляем вершину с мин. кол-вом ребер и повторно отображаем граф.
Ребра не выводятся, так как у нас остался граф, где каждая из вершин имеет минимальное кол-во ребер - одно.

 **Output**:
      ```
Рёбра графа:

```  

11. graph.addEdge(1, 5);
    graph.addEdge(5, 7);
    graph.addEdge(7, 8);
    showGraph.displayEdges();  - добавляем несколько новых ребер. Так как теперь у нас граф пустой, он будет состоять толкьо из этих трех ребер

 **Output**:
      ```
Рёбра графа:
(1 -> 5)
(5 -> 7)
(7 -> 8)

```

12. showGraph.displayGraphProperties(); - отображаем своейста графа

 **Output**:
      ```
Свойства графа:
Граф полный? false
Граф пустой? false
Граф содержит вершины с петлями? false

```
13. graph.sumGraphs(graph2);
    showGraph.displayEdges(); - суммируем графы

 **Output**:
      ```
Рёбра графа:
(1 -> 5)
(5 -> 7)
(7 -> 8)
(8 -> 9)
(9 -> 10)

```    




