import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class holepong extends PApplet {

/* 
Based on emoc's vintage pong
*/

PFont scoreFont;
Ground ground;
Player player1;
Player player2;
Ball ball;
Message message;

public void setup() {
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

public void draw() {
  background(0); stroke(255); fill(255);
  ground.draw();
  player1.draw();
  player2.draw();
  ball.draw();
  message.draw();
}

public void keyPressed() {
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

public void resetGame() {
  player1.setPos(20, height / 2);
  player2.setPos(width - 20, height / 2);
  player1.resetScore();
  player2.resetScore();
  ball.setSticky(1);
  message.set("new game");  
}








class Ball {
  
  float posX, posY, movX, movY, size;
  int stickOn;
  boolean sticky;

  Ball(float _posX, float _posY) {
    posX = _posX;
    posY = _posY;
    movX = 0;
    movY = 0;
    sticky = true;
    stickOn = 1;
    size = 6;
  }

  public void setPos(float _posX, float _posY) {
    posX = _posX;
    posY = _posY;
  }

  public void update() {
    if (sticky) {
      if (stickOn == 1) {
        posX = player1.posX + player1.thickness / 2 + ball.size / 2;
        posY = player1.posY;
      } else if (stickOn == 2) {
        posX = player2.posX - player2.thickness / 2 - ball.size / 2;
        posY = player2.posY;
      }
    } else {
      posX = posX + movX;
      posY = posY + movY;
    }
    

    if (posY < size) movY = -movY;                // top bounce
    if (posY > height-size) movY = -movY;         // bottom bounce

    if (posX < 0) {                               // out left side
      posX = width-2;
      posY = player2.posY;
     /* player2.scorePlus();
      sticky = true;
      stickOn = 1;*/
    } else if (posX > width) {                    // out right side
      posX = 2;
      posY = player1.posY;
     /* player1.scorePlus();
      sticky = true;
      stickOn = 2;*/
    } else if (posX < (player1.posX + (player1.thickness / 2) + (size / 2))) {
      if (player1.testRacketCollision(posX, posY, size)) {
        movX = -movX;
        movY += player1.getMovY() / 5;
      }
    } else if (posX > player2.posX) {
      if (player2.testRacketCollision(posX, posY, size)) {
        movX = -movX;
        movY += player2.getMovY() / 5;
      }
    } else if (abs(posX - width/2) < size/2) {
      movX = -movX;
    }
  }

  public void stickRelease(int idPlayer) {
    sticky = false;
    if ((stickOn == 1) && (idPlayer == 1)) {
      movY = player1.getMovY() + random(2.0f);
      movX = 5;
      stickOn = 0;
    } else if ((stickOn == 2) && (idPlayer == 2)) {
      movY = player2.getMovY();
      movX = -5;
      stickOn = 0;
    }
  }
  
  public void setSticky(int _playerId) {
    sticky = true;
    stickOn = _playerId;
  }

  public float getPosY() {
    return posY;
  }
  
  public void draw() {
    update();
    ellipse(posX, posY, size, size);
  }
}
class Ground {
  
  int scoreP1, scoreP2;

  Ground() {
    scoreP1 = 0;
    scoreP2 = 0;
  }
  
  public void draw() {
    line(width / 2, 0, width / 2, height); // net
    text(player1.getScore(), width / 3, 50);
    text(player2.getScore(), width / 1.6f, 50);
  }
  
    public boolean testNetCollision (float x, float y, float s) {
    if  ((x - (s / 2) > width / 2 - 1) || (x + (s / 2) < width / 2 + 1)) {
      return false;
    } else return true;
  }

}
class Message {
  String say;
  int time;
  int maxTime = 50;

  Message() {
    say = "";
    time = 0;
  }

  public void set(String _say) {
    time = maxTime;
    say = _say;
  }

  public void draw() {
    if ((say != "") && (time > 0)) {
      float a = 255 - ((maxTime - time) * 5);
      stroke(255, 255, 255, a); fill(255, 255, 255, a);
      text(say, width / 2 - (say.length() * 18) / 2, height / 2);
      time --;
    } else {
       say = "";
    }
    stroke(255, 255, 255, 255); fill(255, 255, 255, 255);
  }
}
class Player {
  float posX, posY, movX, movY;
  int id, size, thickness, score;
  boolean computerDriven;
  int difficulty, pause, pauseTime, movPause, dir;

  Player(int _id, float _posX, float _posY, boolean _computerDriven) {
    id = _id;
    posX = _posX;
    posY = _posY;
    computerDriven = _computerDriven;
    size = 80;
    thickness = 6;
    score = 0;
    difficulty = 3;
    pause = 75;
    pauseTime = pause;
    movPause = 0;
    dir = 0;
  }

  public void draw() {
    update();
    //rect(posX - (size / 16), posY - (size / 2), thickness, size);
    rect(posX - (size / 16), 0, thickness, posY - (size / 2));
    rect(posX - (size / 16), posY + (size / 2), thickness, height);
  }

    public void up() {
    if (!computerDriven) movY = constrain (movY -= 12, -10, 10);
  }

  public void down() {
    if (!computerDriven) movY = constrain (movY += 12, -10, 10);
  }

  public void update() {
    if (computerDriven) {
      if (ball.sticky && (ball.stickOn == 2)) pausePlayer();
      else movY = constrain(ball.getPosY() - posY, -2 - difficulty, 2 + difficulty);
    }
    posY = constrain (posY + movY, 0, height);
    movY *= .7f;
  }

  public float getMovY() {
    return movY;
  }

  public void pausePlayer() {
    if (computerDriven) {
      if (pause == pauseTime) {
        movPause = 5;
        dir = 1;
      }
      movY = constrain (movPause * dir, -5, 5);
      if ((posY < (size / 2)) && (dir < 0)) dir = 1;
      else if ((posY > (height - (size / 2))) && (dir > 0)) {
        dir = -1;
      }
      pause --;
      if (pause < 1) {
        pause = 75;
        releaseBall();
      }
    }
  }

  public void releaseBall() {
    ball.stickRelease(id);
  }

  public void scorePlus() {
    score ++;
  }

  public int getScore() {
    return score;
  }

  public void resetScore() {
    score = 0;
  }

  public void setDifficulty(int _d) {
    difficulty = constrain (difficulty += _d, 0, 10);
  }

  public int getDifficulty() {
    return difficulty;
  }

  public void switchComputerDriven() {
    if (computerDriven) computerDriven = false;
    else computerDriven = true;
  }

  public void setPos(int _posX, int _posY) {
    posX = _posX;
    posY = _posY;
  }

  public boolean testRacketCollision (float x, float y, float s) {
    if  ((y - (s / 2) > posY - (size / 2)) && (y + (s / 2) < posY + (size / 2))) {
      return false;
    } else return true;
  }
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "holepong" });
  }
}
