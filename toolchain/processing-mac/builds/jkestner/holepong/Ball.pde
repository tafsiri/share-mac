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

  void setPos(float _posX, float _posY) {
    posX = _posX;
    posY = _posY;
  }

  void update() {
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

  void stickRelease(int idPlayer) {
    sticky = false;
    if ((stickOn == 1) && (idPlayer == 1)) {
      movY = player1.getMovY() + random(2.0);
      movX = 5;
      stickOn = 0;
    } else if ((stickOn == 2) && (idPlayer == 2)) {
      movY = player2.getMovY();
      movX = -5;
      stickOn = 0;
    }
  }
  
  void setSticky(int _playerId) {
    sticky = true;
    stickOn = _playerId;
  }

  float getPosY() {
    return posY;
  }
  
  void draw() {
    update();
    ellipse(posX, posY, size, size);
  }
}
