/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.tests;

import junit.textui.TestRunner;

import org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Global Uncertainty Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GlobalUncertaintyCountermeasureTest extends ArchitecturalCountermeasureTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GlobalUncertaintyCountermeasureTest.class);
	}

	/**
	 * Constructs a new Global Uncertainty Countermeasure test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalUncertaintyCountermeasureTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Global Uncertainty Countermeasure test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected GlobalUncertaintyCountermeasure getFixture() {
		return (GlobalUncertaintyCountermeasure) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(UncertaintyFactory.eINSTANCE.createGlobalUncertaintyCountermeasure());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //GlobalUncertaintyCountermeasureTest
