public abstract class Fruta {
    protected int x;
    protected int y;
    protected int pontos;

    public Fruta(int x, int y, int pontos) {
        this.x = x;
        this.y = y;
        this.pontos = pontos;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getPontos() { return pontos; }

    public abstract void efeito(GamePanel game);
}