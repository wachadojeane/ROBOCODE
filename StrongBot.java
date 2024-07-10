package SUPERJANE;
import robocode.*;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;


// Classe principal do robô que estende a classe robocode.Robot
public class StrongBot extends AdvancedRobot {
	int count = 0;
	double gunTurnAmt;
	
    // Método principal que é executado quando o robô inicia a batalha
    public void run() {
        // Configurações iniciais do robô
        setColors(Color.red, Color.blue, Color.green); // Cores do robô
        // Loop infinito para manter o robô em execução durante a batalha
        while (true) {
            // Movimentação aleatória
            randomMovement();
            // Rotaciona a arma constantemente para procurar oponentes
            turnGunRight(360);
        }
    }
    // Método para movimentação aleatória
    public void randomMovement() {
        // Move para frente uma distância aleatória entre 100 e 200 pixels
        setAhead(Math.random() * 100 + 100);
        // Gira o robô em um ângulo aleatório entre 0 e 360 graus
        setTurnRight(Math.random() * 360);
        // Executa os movimentos
        execute();
    } 
    // Método que é chamado quando o radar detecta um robô inimigo
    public void onScannedRobot(ScannedRobotEvent e) {
        // Chama o método para mirar e atirar no inimigo
        linearTargeting(e);
    }
    // Método de mira linear
    public void linearTargeting(ScannedRobotEvent e) {
        // Obtém a distância até o inimigo
        double distance = e.getDistance();
        // Calcula a direção do inimigo
        double enemyBearing = getHeading() + e.getBearing();
        // Calcula a posição do inimigo
        double enemyX = getX() + distance * Math.sin(Math.toRadians(enemyBearing));
        double enemyY = getY() + distance * Math.cos(Math.toRadians(enemyBearing));
        
        // Calcula o ângulo para atirar
        double angle = Math.toDegrees(Math.atan2(enemyX - getX(), enemyY - getY()));
        // Gira a arma para o ângulo calculado
        turnGunRight(normalRelativeAngleDegrees(angle - getGunHeading()));
        
        // Dispara com potência proporcional à distância do inimigo
        fire(Math.min(400 / distance, 3)); 
    }
}