/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.tests;

import junit.textui.TestRunner;

import org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponent;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Active Component</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ActiveComponentTest extends ArchitecturalPreconditionTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ActiveComponentTest.class);
	}

	/**
	 * Constructs a new Active Component test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActiveComponentTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Active Component test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ActiveComponent getFixture() {
		return (ActiveComponent) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(UncertaintyFactory.eINSTANCE.createActiveComponent());
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

} //ActiveComponentTest
