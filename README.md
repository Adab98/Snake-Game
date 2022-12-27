Source code taken from: https://zetcode.com/javagames/snake/

Changes:
  Larger screen size
  Added background grid for clarity
  Asset Image changes
  Incremental loops
  

Added Features: 
  Enemies
  
   Enemies spawn every 2 apples
      Trigger Enemy respawn if spawn is near Player
      Trigger Enemy respawn if spawn is on Apple
      
   Enemy has chance to move in random direction
    
   Enemy increases by +5 if apple is stolen (Red flash on occurrence currently commented out)
    
   Game end if Enemy touches Snake head

Bug Fix:
  No longer possible to move twice in one turn (e.g making two movements within one redraw cycle)
