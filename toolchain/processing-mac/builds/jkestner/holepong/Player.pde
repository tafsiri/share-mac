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

  void draw() {
    update();
    //rect(posX - (size / 16), posY - (size / 2), thickness, size);
    rect(posX - (size / 16), 0, thickness, posY - (size / 2));
    rect(posX - (size / 16), posY + (size / 2), thickness, height);
  }

    void up() {
    if (!computerDriven) movY = constrain (movY -= 12, -10, 10);
  }

  void down() {
    if (!computerDriven) movY = constrain (movY += 12, -10, 10);
  }

  void update() {
    if (computerDriven) {
      if (ball.sticky && (ball.stickOn == 2)) pausePlayer();
      else movY = constrain(ball.getPosY() - posY, -2 - difficulty, 2 + difficulty);
    }
    posY = constrain (posY + movY, 0, height);
    movY *= .7;
  }

  float getMovY() {
    return movY;
  }

  void pausePlayer() {
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

  void releaseBall() {
    ball.stickRelease(id);
  }

  void scorePlus() {
    score ++;
  }

  int getScore() {
    return score;
  }

  void resetScore() {
    score = 0;
  }

  void setDifficulty(int _d) {
    difficulty = constrain (difficulty += _d, 0, 10);
  }

  int getDifficulty() {
    return difficulty;
  }

  void switchComputerDriven() {
    if (computerDriven) computerDriven = false;
    else computerDriven = true;
  }

  void setPos(int _posX, int _posY) {
    posX = _posX;
    posY = _posY;
  }

  boolean testRacketCollision (float x, float y, float s) {
    if  ((y - (s / 2) > posY - (size / 2)) && (y + (s / 2) < posY + (size / 2))) {
      return false;
    } else return true;
  }
}
