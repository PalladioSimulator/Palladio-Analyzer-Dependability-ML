/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.tests;

import junit.textui.TestRunner;

import org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Deterministic Improvement</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DeterministicImprovementTest extends UncertaintyImprovementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DeterministicImprovementTest.class);
	}

	/**
	 * Constructs a new Deterministic Improvement test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeterministicImprovementTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Deterministic Improvement test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected DeterministicImprovement getFixture() {
		return (DeterministicImprovement) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(UncertaintyFactory.eINSTANCE.createDeterministicImprovement());
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

} //DeterministicImprovementTest
