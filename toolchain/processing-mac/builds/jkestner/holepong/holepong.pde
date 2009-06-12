/* 
Based on emoc's vintage pong
*/

PFont scoreFont;
Ground ground;
Player player1;
Player player2;
Ball ball;
Message message;

void setup() {
  size (600, 450);
  ground = new Ground();
  player1 = new Player(1, 3, height/2, false);
  player2 = new Player(2, width - 3, height / 2, true);
  ball = new Ball(0, 0);
  ball.setPos(player1.posX + player1.size / 9 + ball.size / 2, player1.posY);
  message = new Message();
  scoreFont = createFont("Arial", 40);
  textFont(scoreFont);
  ellipseMode(CENTER);
  frameRate(50);
}

void draw() {
  background(0); stroke(255); fill(255);
  ground.draw();
  player1.draw();
  player2.draw();
  ball.draw();
  message.draw();
}

void keyPressed() {
  if (key == 'r' || key == 'R') {
    resetGame();
  }
  if (key == 'p' || key == 'P') {
    player2.switchComputerDriven();
  }
  if (key == 'e' || key == 'E') {
    player1.up();
  }
  if (key == 'd' || key == 'D') {
    player1.down();
  }
  if (key == 'c' || key == 'C') {
    player1.releaseBall();
  }
  if (key == '+') {
    player2.setDifficulty(1);
    message.set("computer level " + (int)player2.getDifficulty());
  }
  if (key == '-') {
    player2.setDifficulty(-1);
    message.set("computer level " + (int)player2.getDifficulty());
  }
  if (key == CODED) { 
    if (keyCode == UP) {
      player2.up();
    }
    if (keyCode == DOWN) {
      player2.down();
    }
    if (keyCode == LEFT) {
      player2.releaseBall();
    }
  }
}

void resetGame() {
  player1.setPos(20, height / 2);
  player2.setPos(width - 20, height / 2);
  player1.resetScore();
  player2.resetScore();
  ball.setSticky(1);
  message.set("new game");  
}








