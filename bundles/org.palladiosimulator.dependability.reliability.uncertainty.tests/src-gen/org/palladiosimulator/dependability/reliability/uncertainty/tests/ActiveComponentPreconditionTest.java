/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.tests;

import junit.textui.TestRunner;

import org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Active Component Precondition</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ActiveComponentPreconditionTest extends ArchitecturalPreconditionTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ActiveComponentPreconditionTest.class);
	}

	/**
	 * Constructs a new Active Component Precondition test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActiveComponentPreconditionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Active Component Precondition test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ActiveComponentPrecondition getFixture() {
		return (ActiveComponentPrecondition) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(UncertaintyFactory.eINSTANCE.createActiveComponentPrecondition());
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

} //ActiveComponentPreconditionTest
