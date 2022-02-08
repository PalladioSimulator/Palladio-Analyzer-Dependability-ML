/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.tests;

import junit.textui.TestRunner;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyModelEquality;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Model Equality</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class UncertaintyModelEqualityTest extends ArchitecturalPreconditionTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(UncertaintyModelEqualityTest.class);
	}

	/**
	 * Constructs a new Model Equality test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintyModelEqualityTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Model Equality test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected UncertaintyModelEquality getFixture() {
		return (UncertaintyModelEquality) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(UncertaintyFactory.eINSTANCE.createUncertaintyModelEquality());
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

} //UncertaintyModelEqualityTest
