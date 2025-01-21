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

Мы знаем, что каждый класс отвечает за конкретную задачу, разделяя большую программу на несколько маленьких. Я сделала также, разделив программу на четыре "части". Так, например, класс `Edge` отвечает за структуру ребра, класс `Graph` хранит вершины, рёбра и выполняет основные операции (добавление/удаление), а класс `Vertices` реализует анализ свойств вершин и сложные операции (поиск компонент связности, достижимые вершины). Последним является главный класс `Main` для запуска программы, одновременно являющийся тестовой программой.

1. Класс `Edge`:
Класс Edge представляет одно ребро в графе. Ребро связывает две вершины, и каждая вершина может быть связана с другой вершиной. Ребро хранит информацию о вершинах, между которыми оно проходит (от и до).
2. Класс `Graph`:
Класс Graph представляет граф с вершинами и рёбрами. Он отвечает за хранение данных о графе и выполнение различных операций с графом. В нем хранится множество вершин (vertices) и список рёбер (edges).
3. Класс `Vertices`:
Класс Vertices предоставляет методы, которые выполняют более сложные операции с графом, используя его список смежности. Он работает с компонентами связности, достижимостью вершин, а также проверяет наличие вершин, соединённых только с собой.

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

Метод `printEdges` возвращает все рёбра графа в порядке их добавления. Я создаю копию списка ребер

Метод `addEdge` добавляет новое ребро между вершинами from и to. Если ребра или вершины ещё нет, они добавляются.

Метод `removeEdge` удаляет ребро между вершинами from и to.

Метод `removeVertex` удаляет вершину и все рёбра, связанные с ней.

Метод `printVerticesDescending` возвращает список вершин графа, отсортированный по убыванию. По умолчанию метод `sort()` сортирует элементы по возрастанию, мы добавляем специальный компаратор, который сортирует элементы в обратном (или убывающем) порядке. И возвращаем уже отсортированный список вершин.

Метод `getVerticesMore(int x)` возвращает вершины, у которых количество входящих рёбер больше x (заданное значение). 

Метод `removeVerticesWithMinIncomingEdges` удаляет вершины с минимальным количеством входящих рёбер (без учёта петель).

Метод `isComplete` проверяет, является ли граф полным (каждая вершина соединена с каждой).

Метод `isEmpty` проверяет, пуст ли граф.

Метод `buildAdjacencyList` отвечает за построение списка смежности.

Метод `mergeGraphs` объединяет два графа (суммирует вершины и рёбра без дублирования).


