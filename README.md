# EX2 README

## THE MAIN IDEA

EX2 is a project created for an assignment for Object Oriented Programming course in Ariel University. This project is a game about Pokémon’s and agents, the main idea is that the agents need to catch Pokémon’s on a directed weighted graph in a limited amount of time.

## THE ELEMENTS OF THE PROJECT

There are 24 levels, the numbers of the levels are 0 - 23. Every level is different than others by the number of agents in it, the type of the graph, the place where the Pokémon’s spawn (Starting node), the amount of time that the agents have to catch the Pokémon’s and the speed of every agent etc. In every graph there are nodes and between the nodes there are edges. Every edge has its own weight and that means that the larger the weight the slower the agent will move on it. Every node has its own key which is an integer. Every agent has its own speed on every level, the speeds of the agents can be the same on a specific level or all be different. Every Pokémon has its own value, the bigger the value the bigger score we will get when we will catch them. All the Pokémon’s spawn on edges between 2 nodes, the source node and the destination node. Also, there are 2 types of Pokémon’s, green and yellow. The green Pokémon’s are Pokémon’s that their source nodes number is bigger than their destination nodes number, and the yellow Pokémon’s are the Pokémon’s that their source nodes numbers is smaller than their destination nodes numbers. 

## GOING INSIDE THE CODE

### The frontend of the project

In our game we have 2 frames, the login frame and the play frame. The login frame is the frame where you need to enter your ID and the level you want to play. In the play frame the game will print the graph and you will see the game running.

### The backend of the project (How the algorithm works)

There are 2 parts to the “back-end" of the project. The First part is the API of the game which includes the classes of the graph, edges, nodes, locations, and another class for graph algorithms. The usage of the API is to simulate the level of the game that the server has loaded from the client side. Thus, the agents on the client side can decide what is their next step is going to be, since every agent is a thread it all happens in the same time. The second part is the game client side which includes the classes of the agent, Pokémon, commander and arena. At first, we thought to make a commander that will look on all the agents and Pokémon’s and decide what agent catches every Pokémon. The agents would have to request from the commander to follow a Pokémon, and if the commander don’t allow it, they don’t do it. But that method was very problematic because we had too many “moves", the score wasn’t so different from other algorithms methods, and it was too hard to implement and to debug since it’s all multi-threaded. So, we decided to change it and now every agent is going to the Pokémon’s by themselves. Even though we changed our approach of the algorithm, the commander is still in use and he is the one that decides where are the agents placed in the beginning, and the commander updates regularly the agents on their locations and values from the server. Every agent before going to catch a Pokémon looks for the most effective Pokémon to catch. The most effective Pokémon to an agent is the one that is the path to it costs less and has the biggest value. The Agent class represents the agent algorithm and logic, and the Pokémon’s class represents the Pokémon’s logic. The move happens every 100 milliseconds because it is the most optimal time to make a move.
``````
