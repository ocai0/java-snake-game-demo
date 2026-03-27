public class Maca extends Fruta {

    public Maca(int x, int y) {
        super(x, y, 10);
    }
    
    @Override
    public void efeito(GamePanel game) {
        System.out.println("Maçã consumida: +1 no tamanho da cobra");
    }
}
