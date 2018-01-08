

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.omg.CORBA.BAD_CONTEXT;
import static org.junit.Assert.assertEquals;
/** 
* Battle Tester. 
* 
* @author <Authors name> 
* @since <pre>һ�� 8, 2018</pre> 
* @version 1.0 
*/ 
public class BattleTest { 
    Battle bt;
    Ground g=new Ground();
@Before
public void before() throws Exception {
    bt=new Battle(g.positions,g);
} 

@After
public void after() throws Exception {
    int goodcount=0;
    int badcount=0;
    int gc=0;
    int bc=0;
    for(int i=0;i<20;i++)
        for(int j=0;j<20;j++){
            if(g.positions[i][j].isHolderNull())
                continue;
            else if(g.positions[i][j].ReturnHolder().is_evil)
            {
                badcount++;
                if(g.positions[i][j].ReturnHolder().is_alive){
                    bc++;
                }
            }
            else if(!g.positions[i][j].ReturnHolder().is_evil){
                goodcount++;
                if(g.positions[i][j].ReturnHolder().is_alive){
                    gc++;
                }
            }
        }
    int finalcount;
    if(gc<bc)
        finalcount=gc;
    else
        finalcount=bc;

    assertEquals(goodcount,8);
    assertEquals(badcount,8);
} 

/** 
* 
* Method: define_evilwin() 
* 
*/ 
@Test()
public void testDefine_evilwin() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: define_goodwin() 
* 
*/ 
@Test(timeout = 20000)
public void testDefine_goodwin() throws Exception { 
//TODO: Test goes here... 
} 


} 
