/* CSE214 Data Structures Final Exam
   Date: 6/14/2022, 9:00am ~ 11:30am
   Name: Jaehyeon Park
   Student Id: 114338059

   - Total 162 pt

   - In the programming problems, you can ignore the user errors like
     removing from an empty collection or adding to a full collection.
*/

import java.util.*;
@SuppressWarnings("unchecked")
public class ExamFinal {
/*  P1. [13 x 2 pt] What are the terms the explanations below are about?
    Choose your answer from the choices below (some terms can be used more than once):

    amortization, big-omega, big-theta, composite, deque, graph,
    hash, map, priority queue, total order
    separate chaining,

    1. Parameters, local variables, and return address are stored in this area of a java virtual machine: stack

    2. An object is created in this area of a java virtual machine: heap

    3. A design pattern where superclass objects invoke subclass object's methods: template

    4. A design pattern where superclass objects create subclass objects: factory

    5. An asymptotic upper bound of a function disregarding a constant factor: big-oh

    6. A collection of data that maintains the First-In, First-Out principle: queue
    
    7. A collection of data that maintains the Last-In, First-Out principle: stack

    8. The relation that satisfies reflexivity, symmetry, and transitivity?: equivalence

    9. The relation that satisfies reflexivity, antisymmetry, and transitivity?: partial order

    10. In hashing, the number of entries divided by the number of buckets: load factor

    11. In hashing, this method looks for the next empty location when there is a collision: linear proving

    12. A subgraph of a directed graph that has paths between any two vertices: strongly connected component

    13. A tree of a graph that has all vertices of the graph: spanning tree
*/
   
/*  P2. [12 x 2 pt] What are the big-oh of the operations below?

    1. Expected big-oh of adding an entry to a dynamic array: O(n)

    2. Big-oh of adding an entry to a heap: O(log n)

    3. Big-oh of removing the minimum entry from a heap: O(log n)

    4. Expected big-oh of adding an entry to a hash table with the possibility of rehashing: O(1)

    5. Big-oh of sorting n elements using merge sort: O(n log n)

    6. Expected big-oh of sorting n elements using quick sort: O(n log n)

    7. Big-oh of sorting n elements using selection sort: O(n^2)

    8. Big-oh of sorting n elements using heap sort: O(n log n)

    9. Big-oh of sorting n elements using insertion sort: O(n^2)

    10. Big-oh of adding an element to a red-black tree of size n: O(log n)

    11. Big-oh of finding an element in a red-black tree of size n: O(log n)

    12. Big-oh of removing an element from a red-black tree of size n: O(log n)
*/    

/*  P3. [8 x 4 pt] This problem is about Red-Black tree. 
    - Write the pre-order tree traversal result after the operation.
    - Suffix * if the node is a red node.
    For example, the result after adding 2, 1, 3 to the empty tree is: 2, 1*, 3*

    1. Add 6, 3, 7, 4 to the empty Red-Black tree: 6, 3, 4*, 7

    2. Add 5 to the Red-Black tree of Problem 1: 6, 4, 3*, 5*, 7

    3. Add 2 to the Red-Black tree of Problem 2: 6, 4*, 3, 2*, 5, 7

    4. Add 1 to the Red-Black tree of Problem 3: 6, 4*, 2, 1*, 3*, 5, 7

    5. Remove 3 from the Red-Black tree of Problem 4: 6, 4*, 2, 1*, 5, 7

    6. Remove 2 from the Red-Black tree of Problem 5: 6, 4*, 1, 5, 7

    //ignore 7. Remove 0 from the Red-Black tree of Problem 6: X

    8. Remove 1 from the Red-Black tree of Problem 7: 6, 4, 5*, 7
*/

    private static <E> Iterable<E> array2Iterable(E[] arr) {
        //returns Iterable using Lambda and Anonymous class
        return () -> new Iterator<E>() {
            int i = 0;
            public boolean hasNext() { return i < arr.length; }
            public E next()          { return arr[i++]; }
        };
    }

    /*  P4. [5 x 3 pt + 2 x 3 pt] Implement StackImpl using data array and sp
        sp: index of the first empty slot in data
    */
    public static interface Stack<E> {
        public int size();
        public boolean isEmpty();
        public void push(E e);
        public E pop();
        public E top();
    }
    public static class StackImpl<E> implements Stack<E>, Iterable<E>{
        private E[] data;   //entry data
        private int sp;     //stack pointer

        public StackImpl() {
            this(1024);
        }
        public StackImpl(int capacity) {
            data = (E[]) new Object[capacity];
        }

        //TODO: implement Stack<E> 
        @Override
        public int size() {
            return data.length;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public void push(E e) {
            data[sp++] = e;
        }

        @Override
        public E pop() {
            if(isEmpty()) {
                throw new IndexOutOfBoundsException("Stack is empty");
            }
            E e = data[--sp];
            data[sp] = null;
            return e;
        }

        @Override
        public E top() {
            if(isEmpty()) {
                throw new IndexOutOfBoundsException("Stack is empty");
            }
            return data[sp - 1];
        }

        //TODO: implement Iterable<E>: iterate from top to bottom
        //- hint: refer to anonymous Iterator implementation of array2Iterable
        public Iterator<E> iterator() {
            return new Iterator<E>() {//anonymous class
                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < data.length;
                }

                @Override
                public E next() {
                    return data[i++];
                }
            };
        }
    }

    /*  P5. [5 x 3 pt + 2 x 3pt] Implement QueueImpl using data array, f and size
        f: index of the first element in data
        size: the number of entries in the queue
    */
    public static interface Queue<E> {
        public boolean isEmpty();
        public int size();
        public void enqueue(E e);
        public E dequeue();
        public E peek();
    }
    public static class QueueImpl<E> implements Queue<E>, Iterable<E> {
        private E[] data;
        private int f, size;

        public QueueImpl() {
            this(1024);
        }
        public QueueImpl(int capacity) {
            data = (E[]) new Object[capacity];
            f = size = 0;
        }

        //TODO: implement Queue<E>
        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void enqueue(E e) {
            if(size == data.length) {
                throw new IllegalStateException("Queue is full");
            }
            int tmp = (f + size) % data.length;
            data[tmp] = e;
            size++;
        }

        @Override
        public E dequeue() {
            if(size == data.length) {
                throw new IllegalStateException("Queue is empty");
            }
            E result = data[f];
            data[f] = null;
            f = (f + 1) % data.length;
            size--;
            return result;
        }

        @Override
        public E peek() {
            if(isEmpty()) {
                return null;
            }
            return data[f];
        }

        //TODO: implement Iterable<E>
        //- hint: refer to anonymous Iterator implementation of array2Iterable
        public Iterator<E> iterator() {
            return new Iterator<E>() {//anonymous class
                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < size;
                }

                @Override
                public E next() {
                    E result = data[i];
                    i++;
                    return result;
                }
            };
        }
    }

    /*  P6. [2 x 5 pt + 4 x 2pt] Implement the functions below
     */
    private static String[] words = new String[] {
            "eeny", "meeny", "miny", "moe",
            "catch", "a", "tiger", "by", "the", "toe",
            "if", "he", "hollers", "let", "him", "go",
            "eeny", "meeny", "miny", "moe"
    };

    /*  P6.1. Using stack implement the reverse function below
        - If the iter parameter has the strings in the words array below in the order,
        it should return an iterable with {"moe", "miny", "meeny", "eeny", ...}
    */
    public static <E> Iterable<E> reverse(Iterable<E> iter) {
        Stack<E> stack = new StackImpl<>();


        for(E e : iter) {
            stack.push(e);
        }

        Iterator<E> iterator = iter.iterator();
        while(!stack.isEmpty()) {
            iterator.remove();
        }
        return
    }

    /*  P6.2. Implement countWords function using Map.
        - It returns how many times each word in words occurred 
        - The order of the entries in the return value can be arbitrary
        - E.g. if the words parameter has the strings in the words array above,
               it should return an iterable with {"a":1, "eeny":2, "meeny":2, "catch":1, "miny":2, ...}
    */
    public static Iterable<Map.Entry<String, Integer>> countWords(Iterable<String> words) {
        Map<String, Integer> count = new HashMap<>(); //word -> count of word map


        return count.entrySet();
    }

    /*  P6.3. Implement quickSort function
     */
    public static <E extends Comparable<E>> Iterable<E> quickSort(Iterable<E> iter) {
        List<E> res = new ArrayList<>();

        //TODO: if iter does not have any element return res
        Iterator<E> i = iter.iterator();

        while (!i.hasNext()) {
            return res;
        }

        //TODO: pivot is the first element of iter
        E pivot = i.next();

        List<E> lt = new ArrayList<>();
        List<E> eq = new ArrayList<>();
        List<E> gt = new ArrayList<>();
        for(E e : iter) {
            int cmp = e.compareTo(pivot);
            //TODO: put the elements in iter to lt, eq, and gt depending on cmp
            if(cmp == 0) {
                eq.add(e);
            }
            else if(cmp < 0) {
                lt.add(e);
            }
            else {
                gt.add(e);
            }
        }

        //TODO: sort the lists if necessary and add the results to res
        quickSort(lt);
        quickSort(gt);
        res.clear();
        for(E e : lt) {
            res.add(e);
        }
        for(E e : eq) {
            res.add(e);

        }
        for(E e : gt) {
            res.add(e);
        }

        return res;
    }

    /*  P7. [4 x 5 pt] This problem is about a graph
        Node is the vertex of a graph
    */
    public static class Node implements Comparable<Node> {
        public String word;
        public Map<Node, Integer> edges;    //opposite node and the weight to the node
        public Node(String word) {
            this.word = word;
            edges = new HashMap<>();
        }
        public int compareTo(Node n) {
            return word.compareTo(n.word);
        }
        public boolean equals(Object o) {
            return word.equals(o);
        }
        public int hashCode() {
            //TODO: implement 6-bit cyclic shift hash code on word
            int h = 0;


            return h;
        }
    }

    //nodes is a map of the word of a Node to the Node itself
    //nodes contains all Nodes of a graph
    static Map<String, Node> nodes;

    public static void dfs(Node from, Map<Node, Node> tree/*dst -> src map*/) {
        List<Node> known = new ArrayList<>();
        dfs(from, known, tree);
    }
    private static void dfs(Node src, List<Node> known, Map<Node, Node> tree/*dst -> src map*/) {
        //TODO: implement the depth first search using recursion
        //  Visit neighbors in the ascending order of their words
        //- hint: use quickSort(src.edges.keySet()) to get the neighbors of src


    }

    public static void dfs2(Node from, Map<Node, Node> tree/*dst -> src map*/) {
        List<Node> known = new ArrayList<>();
        Stack<Node> stack = new StackImpl<>();

        //TODO: re-implement the depth first search using stack
        //  Visit neighbors in the ascending order of their words
        //- hint: use reverse(quickSort(src.edges.keySet())) to get the neighbors of src
        //- hint: push and pop two nodes (a source and then a destination) together for tree.put
        stack.push(null);
        stack.push(from);
        while(!stack.isEmpty()) {
            //pop src and dst from stack in the right order
            Node dst =
                    Node src =

                    //put src at dst of tree unless src is null


                    //add dst to known


                    src = dst;
            //for each neighbor of src, unless it is known, push it with src to stack (in thr right order)


        }
    }

    public static void bfs(Node from, Map<Node, Node> tree/*dst -> src map*/) {
        List<Node> known = new ArrayList<>();
        Queue<Node> queue = new QueueImpl<>();

        //TODO: implement the breadth first search using queue
        //  Visit neighbors in the ascending order of their words
        //- hint: use quickSort(src.edges.keySet()) to get the neighbors of src


    }

    /*  Test codes from now on
     */
    public static void buildGraph(Iterable<String> words) {
        nodes = new HashMap<>();
        int i = 0;
        for(String word : words) {
            if(nodes.get(word) != null)
                word = word + i++;
            nodes.put(word, new Node(word));
        }
        Iterable<String> sorted = quickSort(nodes.keySet());
        for(String u : sorted) {
            for(String v : sorted) {
                if(v.length() - u.length() > 0 &&
                        v.length() - u.length() < 3) {
                    Node src = nodes.get(u);
                    Node dst = nodes.get(v);
                    int weight = v.length() - u.length();
                    src.edges.put(dst, weight);
                }
            }
        }
    }
    public static void printTree(Map<Node, Node> tree/*dst -> src map*/) {
        for(Node dst : quickSort(tree.keySet())) {
            Node src = tree.get(dst);
            if(src != null)
                System.out.format("(%s->%s), ", src.word, dst.word);
        }
        System.out.println();
    }
    public static void test() {
/* expected results
    test stack
    moe, miny, meeny, eeny, go, him, let, hollers, he, if, toe, the, by, tiger, a, catch, moe, miny, meeny, eeny,

    test queue
    eeny, meeny, miny, moe, catch, a, tiger, by, the, toe, if, he, hollers, let, him, go, eeny, meeny, miny, moe,
    
    test countWords
    a:1, moe:2, eeny:2, go:1, hollers:1, toe:1, tiger:1, him:1, the:1, miny:2, meeny:2, by:1, let:1, catch:1, if:1, he:1,
    
    test reverse
    moe, miny, meeny, eeny, go, him, let, hollers, he, if, toe, the, by, tiger, a, catch, moe, miny, meeny, eeny,
    
    test quickSort
    a, by, catch, eeny, eeny, go, he, him, hollers, if, let, meeny, meeny, miny, miny, moe, moe, the, tiger, toe,
    
    test dfs
    (a->by), (moe3->catch), (toe->eeny), (moe3->eeny0), (a->go), (a->he), (if->him), (meeny1->hollers), (a->if), (a->let),
    (moe3->meeny), (tiger->meeny1), (toe->miny), (moe3->miny2), (a->moe), (toe->moe3), (a->the), (toe->tiger), (a->toe),

    test dfs2
    (a->by), (moe3->catch), (toe->eeny), (moe3->eeny0), (a->go), (a->he), (if->him), (meeny1->hollers), (a->if), (a->let),
    (moe3->meeny), (tiger->meeny1), (toe->miny), (moe3->miny2), (a->moe), (toe->moe3), (a->the), (toe->tiger), (a->toe),

    test bfs
    (a->by), (moe3->catch), (toe->eeny), (moe3->eeny0), (a->go), (a->he), (if->him), (meeny1->hollers), (a->if), (if->let),
    (moe3->meeny), (tiger->meeny1), (toe->miny), (moe3->miny2), (if->moe), (toe->moe3), (if->the), (moe3->tiger), (if->toe),    
*/
        System.out.println("test stack");
        StackImpl<String> stack = new StackImpl<>();
        for(String word : array2Iterable(words))
            stack.push(word);
        for(String word : stack)
            System.out.format("%s, ", word);
        System.out.println();

        System.out.println("test queue");
        QueueImpl<String> queue = new QueueImpl<>();
        for(String word : array2Iterable(words))
            queue.enqueue(word);
        for(String word : queue)
            System.out.format("%s, ", word);
        System.out.println();

        System.out.println("test countWords");
        for(Map.Entry<String, Integer> pair : countWords(array2Iterable(words)))
            System.out.format("%s:%d, ", pair.getKey(), pair.getValue());
        System.out.println();

        System.out.println("test reverse");
        for(String word : reverse(array2Iterable(words)))
            System.out.format("%s, ", word);
        System.out.println();

        System.out.println("test quickSort");
        for(String word : quickSort(array2Iterable(words)))
            System.out.format("%s, ", word);
        System.out.println();

        //graph
        buildGraph(array2Iterable(words));

        System.out.println("test dfs");
        Map<Node, Node> tree = new HashMap<>(); /*dst -> src map*/
        dfs(nodes.get("a"), tree);
        printTree(tree);

        System.out.println("test dfs2");
        tree = new HashMap<>(); /*dst -> src map*/
        dfs2(nodes.get("a"), tree);
        printTree(tree);

        System.out.println("test bfs");
        tree = new HashMap<>(); /*dst -> src map*/
        bfs(nodes.get("a"), tree);
        printTree(tree);
    }

    public static void main(String[] args) {
        ExamFinal.test();
    }
}