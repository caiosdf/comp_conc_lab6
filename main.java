class Vec {
    //recurso compartilhado
    int[] vector = new int[10];  //vetor a ser incrementado
    int nthreads; //numero de threads
    //construtor
    public Vec(int nthreads) { 
        this.nthreads = nthreads;
        for(int i = 0; i < vector.length; i++){
            vector[i] = 0;
        }
    }

    public synchronized void print() { 
        for(int i = 0; i < vector.length; i++){
            System.out.print(vector[i] + " ");
        }
        System.out.print("\n");
    }
  
}

//classe que estende Thread e implementa a tarefa de cada thread do programa 
class T extends Thread {
    //identificador da thread
    private int id;
    //objeto compartilhado com outras threads
    Vec vec;
  
    //construtor
    public T(int tid, Vec vec) { 
        this.id = tid; 
        this.vec = vec;
    }

    //metodo main da thread
    public void run() {
        System.out.println("Thread " + this.id + " iniciou!");
        for (int i=this.id; i<this.vec.vector.length; i+=this.vec.nthreads) {
            this.vec.vector[i]++; 
        }
        System.out.println("Thread " + this.id + " terminou!"); 
    }
}

class VecIncrement {
    static final int N = 2;

    public static void main (String[] args) {
        //reserva espaço para um vetor de threads
        Thread[] threads = new Thread[N];

        //cria uma instancia do recurso compartilhado entre as threads
        Vec vec = new Vec(N);

        //exibe o vetor de entrada
        vec.print();

        //cria as threads da aplicacao
        for (int i=0; i<threads.length; i++) {
            threads[i] = new T(i, vec);
        }

        //inicia as threads
        for (int i=0; i<threads.length; i++) {
            threads[i].start();
        }

        //espera pelo termino de todas as threads
        for (int i=0; i<threads.length; i++) {
            try { threads[i].join(); } catch (InterruptedException e) { return; }
        }

        //exibe o vetor de saída
        vec.print();
    }
}