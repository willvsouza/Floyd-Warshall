import java.util.ArrayList;
import java.util.List;

class Main
{
    // Função recursivo para imprimir o caminho de um determinado vértice `u` do vértice de origem `v`
    private static void printPath(int[][] path, int v, int u, List<Integer> route)
    {
        if (path[v][u] == v) {
            return;
        }
        printPath(path, v, path[v][u], route);
        route.add(path[v][u]);
    }

    // Função para imprimir o menor custo com informações de caminho entre
    // todos os pares de vértices
    private static void printSolution(int[][] path, int n)
    {
        for (int v = 0; v < n; v++)
        {
            for (int u = 0; u < n; u++)
            {
                if (u != v && path[v][u] != -1)
                {
                    List<Integer> route = new ArrayList<>();
                    route.add(v);
                    printPath(path, v, u, route);
                    route.add(u);
                    System.out.printf("O menor caminho de %d —> %d é %s\n",
                            v, u, route);
                }
            }
        }
    }

    // Função para executar o algoritmo Floyd–Warshall
    public static void floydWarshall(int[][] adjMatrix)
    {
        // caso base
        if (adjMatrix ==null || adjMatrix.length == 0) {
            return;
        }

        // número total de vértices no `adjMatrix`
        int n = adjMatrix.length;

        // cost[] e path[] armazena o caminho mais curto
        // informações (custo mais curto/rota mais curta)
        int[][] cost = new int[n][n];
        int[][] path = new int[n][n];

        // inicializa cost[] e path[]
        for (int v = 0; v < n; v++)
        {
            for (int u = 0; u < n; u++)
            {
                // inicialmente, o custo seria o mesmo que o peso da aresta
                cost[v][u] = adjMatrix[v][u];

                if (v == u) {
                    path[v][u] = 0;
                }
                else if (cost[v][u] != Integer.MAX_VALUE) {
                    path[v][u] = v;
                }
                else {
                    path[v][u] = -1;
                }
            }
        }

        // executa Floyd–Warshall
        for (int k = 0; k < n; k++)
        {
            for (int v = 0; v < n; v++)
            {
                for (int u = 0; u < n; u++)
                {
                    // Se o vértice `k` estiver no caminho mais curto de `v` para `u`,
                    // então atualiza o valor de cost[v][u] e path[v][u]

                    if (cost[v][k] != Integer.MAX_VALUE
                            && cost[k][u] != Integer.MAX_VALUE
                            && (cost[v][k] + cost[k][u] < cost[v][u]))
                    {
                        cost[v][u] = cost[v][k] + cost[k][u];
                        path[v][u] = path[k][u];
                    }
                }

                // se os elementos diagonais se tornarem negativos, o
                // gráfico contém um ciclo de peso negativo
                if (cost[v][v] < 0)
                {
                    System.out.println("Negative-weight cycle found!!");
                    return;
                }
            }
        }

        // Imprime o caminho mais curto entre todos os pares de vértices
        printSolution(path, n);
    }

    public static void main(String[] args)
    {
        // define o infinito
        int I = Integer.MAX_VALUE;

        // dada representação de adjacência da matriz
        int[][] adjMatrix = new int[][]
                {
                        { 0, I, -2, I },
                        { 4, 0, 3, I },
                        { I, I, 0, 2 },
                        { I, -1, I, 0 }
                };

        //Executa o algoritmo Floyd–Warshall
        floydWarshall(adjMatrix);
    }
}