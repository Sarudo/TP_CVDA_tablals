package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestAutour {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
    public void testAutourCentre(){
        int[][] tTest=new int[4][4];
        tTest=Jeu.Demineur.creationTabBombe(1,4);
        assertEquals(9,Jeu.Demineur.autour(2,2,4,tTest));
    }
    
    @Test
    public void testAutourBord(){
        int[][] tTest=new int[4][4];
        tTest=Jeu.Demineur.creationTabBombe(1,4);
        assertEquals(4,Jeu.Demineur.autour(0,0,4,tTest));
    }

}
