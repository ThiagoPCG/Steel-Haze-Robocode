package Astral;

import robocode.*;
import java.awt.Color;

public class SteelHaze extends AdvancedRobot {

    boolean inimigoDetectado = false; // Define inicialmente como falsa a detecção do inimigo.

    public void run() {
        // Inicialização do robô
        setColors(Color.black, Color.black, Color.black); // Define as cores do robô
        setAdjustRadarForGunTurn(true); // Ativa a sincronização entre radar e canhão
        setAdjustGunForRobotTurn(true); // Ativa a sincronização entre canhão e radar

        while (true) {
            // Comportamento do robô
			// O robô sempre estará rodando em circulos para sempre se evadir dos projéteis inimigos
			
            if (!inimigoDetectado) { //Caso um inimigo seja detectado, o robô ficará rodando e girando o radar.
            setTurnRadarLeft(360);
            setTurnRight(5000);
			setMaxVelocity(4);
			ahead(10000);
            execute();
				
            }
			//Caso um inimigo NÃO seja detectado, o robô continuará rodando e girando o radar.
			setTurnRadarLeft(360);
            setTurnRight(5000);
			setMaxVelocity(4);
			ahead(10000);
            execute();
    }
    }
	
    public void onScannedRobot(ScannedRobotEvent e) {
        // Comportamento quando outro robô é detectado

        inimigoDetectado = true; // Define como verdadeira a detecção do inimigo.
		
        double distancia = e.getDistance(); // Calcula a distância do inimigo.
		
        double bearing = getHeadingRadians() + e.getBearingRadians(); // Calcula o ângulo absoluto do inimigo somando a direção em que o robô está virado e a localização do robô inimigo detectado dada em radians.
		
        double radarTurn = robocode.util.Utils.normalRelativeAngle(bearing - getRadarHeadingRadians()); // Subtrai o ângulo absoluto do robô inimigo pela direção em que o radar está apontado.
        setTurnRadarRightRadians(radarTurn); // Faz o radar focar na direção do inimigo automaticamente.       
		
        double gunTurn = robocode.util.Utils.normalRelativeAngle(bearing - getGunHeadingRadians()); // Subtrai o ângulo absoluto do robô inimigo pela direção em que o canhão está apontado.
        setTurnGunRightRadians(gunTurn); // Faz o canhão focar na direção do inimigo automaticamente.        
		
        double potenciaTiro = Math.min(3, 1200 / distancia); // Calcula a potência do tiro com base na distância mínima do inimigo.       
        fire(potenciaTiro);  // Atira no inimigo com a potência calculada.
		
    }

    public void onHitByBullet(HitByBulletEvent e) {
	     // Comportamento quando atingido por uma bala.
            setTurnRadarRight(360); // Faz uma rotação em 360 para recalibrar o radar.
			setMaxVelocity(8); // Aumenta a velocidade para tentar desviar dos projéteis.
    }

    public void onHitWall(HitWallEvent e) {
        // Comportamento quando atinge uma parede.
			back(100); // Se locomove para trás mas continua rodando para direita.
    }
	
	public void onHitRobot(HitRobotEvent event) {
        // Comportamento quando o robô atinge outro robô.
   	        back(100); // Se locomove para trás mas continua rodando para direita.
}
}
