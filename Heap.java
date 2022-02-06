public class Heap {
    private Node[] heap;
    private int size;
    private int maxsize;

    public int getSize() {
        return size;
    }

    public Heap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        heap = new Node[maxsize];
    }

    private void swap(int index1,int index2)
    {
        Node tmp;
        tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }


    public  boolean hasParent(int index){
        return index > 1;
    }

    private void heapifyUp() {
        int index = size;
        while(hasParent(index)){
            if(heap[index].freq < heap[index/2].freq) {
                swap(index,index/2);
                index = index/2;
            }
            else break;
        }
    }
     private void heapifyDown(){
        int i = 1;
        boolean sw = false;

         while (2*i<=size){
             sw = false;
             if (heap[2 * i].freq < heap[(2 * i) + 1].freq) {
                 if (heap[i].freq > heap[2 * i].freq) {
                     swap(i, 2*i);
                     i = 2 * i;
                     sw = true;
                 }
             } else {
                 if (heap[i].freq > heap[(2 * i) + 1].freq) {
                     swap(i, 2*i + 1);
                     i = 2 * i + 1 ;
                     sw = true;
                 }
             }
         if(!sw) break;
         }

     }

    public void insert(Node node)
    {
        if (size >= maxsize) {
            return;
        }
        heap[++size] = node;
        heapifyUp();
    }

    public Node pop() {
        Node popped = heap[1];
        heap[1] = heap[size--];
        heapifyDown();
        return popped;
    }

}
