package no.knalum.actor;

import static no.knalum.map.IsometricMapRenderer.SZ_X;

enum MoveAction {
    RIGHT(SZ_X,0);

    private final int dx;
    private final int dy;

    MoveAction(final int dx, final int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
