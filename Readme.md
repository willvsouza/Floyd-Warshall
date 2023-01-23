Caminhos mais curtos de todos os pares – Algoritmo Floyd-Warshall

(Fonte: https://www.techiedelight.com/pt/pairs-shortest-paths-floyd-warshall-algorithm/)

Dado um conjunto de vértices V em um grafo ponderado onde sua aresta pesa w(u, v) pode ser negativo, encontre os pesos do caminho mais curto d(s, v) de todas as fontes s para todos os vértices v presente no gráfico. Se o gráfico contiver um peso negativo ciclo, denuncie.

Por exemplo, considere o gráfico a seguir:

Floyd Warshall Algorithm

The adjacency matrix containing the shortest distance is:
 
  0  -1  -2   0
  4   0   2   4
  5   1   0   2
  3  -1   1   0
 
The shortest path from:
 
• vertex 0 to vertex 1 is [0 —> 2 —> 3 —> 1]
• vertex 0 to vertex 2 is [0 —> 2]
• vertex 0 to vertex 3 is [0 —> 2 —> 3]
• vertex 1 to vertex 0 is [1 —> 0]
• vertex 1 to vertex 2 is [1 —> 0 —> 2]
• vertex 1 to vertex 3 is [1 —> 0 —> 2 —> 3]
• vertex 2 to vertex 0 is [2 —> 3 —> 1 —> 0]
• vertex 2 to vertex 1 is [2 —> 3 —> 1]
• vertex 2 to vertex 3 is [2 —> 3]
• vertex 3 to vertex 0 is [3 —> 1 —> 0]
• vertex 3 to vertex 1 is [3 —> 1]
• vertex 3 to vertex 2 is [3 —> 1 —> 0 —> 2]
Nós já cobrimos fonte única os caminhos mais curtos em postos separados. Vimos que:

Para gráficos com pesos de aresta não negativos, Algoritmo de Dijkstra corre em O(E + V × log(V))
Para gráficos contendo pesos de arestas negativos, Bellman–Ford corre em O(V × E).
Para um DAG, um passe de Bellman-Ford (chamado passo de relaxamento) é o suficiente que vai levar O(V + E) Tempo.
Aqui, V é o número total de vértices e E é o número total de arestas no grafo.

 
Este post vai apresentar Caminhos mais curtos de todos os pares que retornam os caminhos mais curtos entre cada par de vértices no gráfico contendo pesos de arestas negativos.

Pratique Este Problema

Se o gráfico contiver apenas pesos de aresta positivos, uma solução simples seria executar o algoritmo de Dijkstra V vezes. A complexidade de tempo desta solução seria O(V × (E + V × log(V))), ou seja, O(V × E + V2.log(V)).

Se o grafo contiver pesos de arestas negativos, podemos executar Bellman–Ford uma vez de cada vértice para encontrar os caminhos mais curtos de todos os pares. A complexidade de tempo desta abordagem será O(V2 × E). Se o gráfico é denso, ou seja, E = V2, então a complexidade de tempo se torna O(V4).
 

Algoritmo Floyd-Warshall é um algoritmo para encontrar os caminhos mais curtos em um gráfico ponderado com pesos de aresta positivos ou negativos (mas sem ciclos negativos). Ele faz isso comparando todos os caminhos possíveis através do gráfico entre cada par de vértices e isso também com O(V3) comparações em um gráfico.

A seguir está o pseudocódigo para Floyd Warshall, conforme fornecido na Wikipedia. A implementação pega um gráfico, representado por uma matriz de adjacência, e preenche dist[] com informações de caminho mais curto (o menor custo):

let dist be V × V matrix of minimum distances initialized to INFINITY
for each vertex v
  dist[v][v] = 0
for each edge (u, v)
  dist[u][v] = weight(u, v)
 
for k from 0 to |V| – 1
  for i from 0 to |V| – 1
    for j from 0 to |V| – 1
      if (dist[i][k] + dist[k][j]) is less than dist[i][j] then
        dist[i][j] = dist[i][k] + dist[k][j]
Acima pseudocódigo pega um vértice k entre 0 e V-1, um de cada vez, e incluir esse vértice como um vértice intermediário no caminho mais curto entre cada par de arestas i—>j no gráfico. Ele atualiza a matriz de custos sempre que um caminho mais direto de i para j através do vértice k seja encontrado. Uma vez que, para um dado k, já consideramos os vértices 0…k-1 como vértices intermediários, essa abordagem funciona.

 
Vamos considerar o gráfico acima,

Antes da primeira iteração do loop externo para k, os únicos caminhos conhecidos correspondem às arestas simples no grafo.

 
Floyd Warshall

No k = 0, os caminhos que passam pelo vértice 0 são encontrados: em particular, o caminho [1, 0, 2] for encontrado, substituindo o caminho [1, 2] que tem menos arestas, mas é caro.
 
Floyd Warshall Algorithm: Step 0

No k = 1, caminhos que passam pelos vértices {0, 1} são encontrados. A figura a seguir mostra como o caminho [3, 1, 0, 2] é montado a partir dos dois caminhos conhecidos [3, 1] e [1, 0, 2] encontrado em iterações anteriores, com 1 na interseção. O caminho [3, 1, 2] não é considerado porque [1, 0, 2] é o caminho mais curto encontrado até agora de 1 para 2.
 
Floyd Warshall Algorithm: Step 1

No k = 2, caminhos que passam pelos vértices {0, 1, 2} são encontrados.
 
Floyd Warshall Algorithm: Step 2

Finalmente, em k = 3, todos os caminhos mais curtos são encontrados.
 
Floyd Warshall Algorithm: Step 3

O algoritmo Floyd-Warshall é simples de codificar e muito eficiente tradicionalmente. Também pode ser usado para encontrar o Fechamento transitivo de um gráfico e detectar ciclos de peso negativo no gráfico.

Para detectar ciclos negativos usando o algoritmo Floyd-Warshall, verifique se há um número negativo na diagonal da matriz de distância, pois indica que o gráfico contém pelo menos um ciclo negativo.

Como isso funciona?

O algoritmo Floyd-Warshall revisa iterativamente os comprimentos dos caminhos entre todos os pares de vértices (i, j), incluindo onde i = j. Inicialmente, o tamanho do caminho (i, i) é zero. Um caminho [i, k…i] só pode melhorar isso se tiver um comprimento menor que zero, ou seja, denota um ciclo negativo. Assim, após o algoritmo, (i, i) será negativo se existir um caminho de comprimento negativo de i de volta a i.