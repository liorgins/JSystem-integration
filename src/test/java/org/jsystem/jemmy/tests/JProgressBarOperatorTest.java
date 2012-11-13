/*
 * $Id: JProgressBarOperatorTest.java,v 1.6 2006/06/30 14:00:52 jtulach Exp $
 *
 * ---------------------------------------------------------------------------
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]".
 *
 * The Original Software is the Jemmy library. The Initial Developer of the
 * Original Software is Alexandre Iline. All Rights Reserved.
 *
 * ---------------------------------------------------------------------------
 *
 * Contributor(s): Manfred Riem (mriem@netbeans.org).
 */
package org.jsystem.jemmy.tests;

import java.util.Hashtable;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ProgressBarUI;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JProgressBarOperator;
import org.netbeans.jemmy.operators.JProgressBarOperator.ValueChooser;
import org.netbeans.jemmy.util.NameComponentChooser;

/**
 * A JUnit test for JProgressBarOperator.
 *
 * @author Manfred Riem (mriem@netbeans.org)
 * @version $Revision: 1.6 $
 */
public class JProgressBarOperatorTest extends TestCase {
    /**
     * Stores the frame.
     */
    private JFrame frame;
    
    /**
     * Stores the progress bar.
     */
    private JProgressBar progressBar;
    
    /**
     * Constructor.
     *
     * @param testName the name of the test.
     */
    public JProgressBarOperatorTest(String testName) {
        super(testName);
    }
    
    /**
     * Setup for testing.
     */
    protected void setUp() throws Exception {
        frame = new JFrame();
        progressBar = new JProgressBar();
        progressBar.setName("JProgressBarOperatorTest");
        frame.getContentPane().add(progressBar);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Cleanup after testing.
     */
    protected void tearDown() throws Exception {
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }

    /**
     * Suite method.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(JProgressBarOperatorTest.class);
        
        return suite;
    }
    
    /**
     * Test constructor.
     */
    public void testConstructor() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);

        JProgressBarOperator operator2 = new JProgressBarOperator(operator, new NameComponentChooser("JProgressBarOperatorTest"));
        assertNotNull(operator2);
        
        JProgressBarOperator operator3 = new JProgressBarOperator(progressBar);
        assertNotNull(operator3);
    }

    /**
     * Test findJProgressBar method.
     */
    public void testFindJProgressBar() {
        frame.setVisible(true);

        JProgressBar progressBar1 = JProgressBarOperator.findJProgressBar(frame);
        assertNotNull(progressBar1);
        
        JProgressBar progressBar2 = JProgressBarOperator.findJProgressBar(frame, new NameComponentChooser("JProgressBarOperatorTest"));
        assertNotNull(progressBar2);
    }

    /**
     * Test waitJProgressBar method.
     */
    public void testWaitJProgressBar() {
        frame.setVisible(true);

        JProgressBar progressBar1 = JProgressBarOperator.waitJProgressBar(frame);
        assertNotNull(progressBar1);
        
        JProgressBar progressBar2 = JProgressBarOperator.waitJProgressBar(frame, new NameComponentChooser("JProgressBarOperatorTest"));
        assertNotNull(progressBar2);
    }

    /**
     * Test waitValue method.
     */
    public void testWaitValue() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setValue(10);
        operator1.waitValue(10);
        assertEquals(10, operator1.getValue());

        operator1.waitValue("10");
        assertEquals(10, operator1.getValue());
        
        operator1.waitValue(new ValueChooserTest());
    }
    
    /**
     * An inner class needed for testing.
     */
    public class ValueChooserTest implements ValueChooser {
        public boolean checkValue(int value) {
            return true;
        }

        public String getDescription() {
            return "Booh!";
        }
    }

    /**
     * Test getDump method.
     */
    public void testGetDump() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);

        operator1.setValue(10);
        Hashtable hashtable = operator1.getDump();
        
        assertEquals("10", hashtable.get(JProgressBarOperator.VALUE_DPROP));
    }

    /**
     * Test addChangeListener method.
     */
    public void testAddChangeListener() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        ChangeListenerTest listener = new ChangeListenerTest();
        operator1.addChangeListener(listener);
        assertEquals(2, progressBar.getChangeListeners().length);
        
        operator1.removeChangeListener(listener);
        assertEquals(1, progressBar.getChangeListeners().length);
    }
    
    /**
     * Inner class needed for testing.
     */
    public class ChangeListenerTest implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
        }
    }

    /**
     * Test getMaximum method.
     */
    public void testGetMaximum() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setMaximum(101);
        assertEquals(101, operator1.getMaximum());
        assertEquals(101, progressBar.getMaximum());
    }

    /**
     * Test getMinimum method.
     */
    public void testGetMinimum() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setMinimum(7);
        assertEquals(7, operator1.getMinimum());
        assertEquals(7, progressBar.getMinimum());
    }

    /**
     * Test getModel method.
     */
    public void testGetModel() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        BoundedRangeModelTest model = new BoundedRangeModelTest();
        operator1.setModel(model);
        assertEquals(model, operator1.getModel());
        assertEquals(model, progressBar.getModel());
    }
    
    /**
     * An inner class needed for testing.
     */
    public class BoundedRangeModelTest implements BoundedRangeModel {
        public int getMinimum() {
            return -1;
        }

        public void setMinimum(int newMinimum) {
        }

        public int getMaximum() {
            return -1;
        }

        public void setMaximum(int newMaximum) {
        }

        public int getValue() {
            return -1;
        }

        public void setValue(int newValue) {
        }

        public void setValueIsAdjusting(boolean b) {
        }

        public boolean getValueIsAdjusting() {
            return false;
        }

        public int getExtent() {
            return -1;
        }

        public void setExtent(int newExtent) {
        }

        public void setRangeProperties(int value, int extent, int min, int max, boolean adjusting) {
        }

        public void addChangeListener(ChangeListener x) {
        }

        public void removeChangeListener(ChangeListener x) {
        }
    }

    /**
     * Test getOrientation method.
     */
    public void testGetOrientation() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setOrientation(JProgressBar.VERTICAL);
        assertEquals(JProgressBar.VERTICAL, operator1.getOrientation());
        assertEquals(JProgressBar.VERTICAL, progressBar.getOrientation());
    }

    /**
     * Test getPercentComplete method.
     */
    public void testGetPercentComplete() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setMinimum(0);
        operator1.setMaximum(100);
        operator1.setValue(50);
        assertTrue(0.5 == operator1.getPercentComplete());
        assertTrue(0.5 == progressBar.getPercentComplete());
    }

    /**
     * Test getString method.
     */
    public void testGetString() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setString("BLABLA");
        assertEquals("BLABLA", operator1.getString());
        assertEquals("BLABLA", progressBar.getString());
    }

    /**
     * Test getUI method.
     */
    public void testGetUI() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        ProgressBarUITest progressBarUI = new ProgressBarUITest();
        operator1.setUI(progressBarUI);
        assertEquals(progressBarUI, operator1.getUI());
        assertEquals(progressBarUI, progressBar.getUI());
    }
    
    /**
     * Inner class needed for testing.
     */
    public class ProgressBarUITest extends ProgressBarUI {
    }

    /**
     * Test isBorderPainted method.
     */
    public void testIsBorderPainted() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setBorderPainted(true);
        assertTrue(operator1.isBorderPainted());
        assertTrue(progressBar.isBorderPainted());
        
        operator1.setBorderPainted(false);
        assertTrue(!operator1.isBorderPainted());
        assertTrue(!progressBar.isBorderPainted());
    }

    /**
     * Test isStringPainted method.
     */
    public void testIsStringPainted() {
        frame.setVisible(true);
        
        JFrameOperator operator = new JFrameOperator();
        assertNotNull(operator);
        
        JProgressBarOperator operator1 = new JProgressBarOperator(operator);
        assertNotNull(operator1);
        
        operator1.setStringPainted(true);
        assertTrue(operator1.isStringPainted());
        assertTrue(progressBar.isStringPainted());
        
        operator1.setStringPainted(false);
        assertTrue(!progressBar.isStringPainted());
    }
}
