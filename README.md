# CS-463---Dancers

The scene: there is a dancing competition. There 8 dances and a set number of dancers. Each dancer has a sheet with each dance along with their partner for the dance. A dancer cannot dance with the same partner more than twice.

The goal: have each dancer fill their dancing sheet as much as possible.

The way I did it: created a Dancer, then extended to a Leader and a Follower (which each implement Runnable). The Leaders send out requests and the Followers wait respond if their sheet is not full. The Leaders wait to hear a response, and if they do not in time then they send out another request to a random Follower. 
