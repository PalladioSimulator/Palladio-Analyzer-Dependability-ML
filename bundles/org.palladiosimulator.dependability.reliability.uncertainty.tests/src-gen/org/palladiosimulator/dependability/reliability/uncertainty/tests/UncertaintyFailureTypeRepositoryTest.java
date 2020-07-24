/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Failure Type Repository</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class UncertaintyFailureTypeRepositoryTest extends TestCase {

	/**
	 * The fixture for this Failure Type Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UncertaintyFailureTypeRepository fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(UncertaintyFailureTypeRepositoryTest.class);
	}

	/**
	 * Constructs a new Failure Type Repository test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintyFailureTypeRepositoryTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Failure Type Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(UncertaintyFailureTypeRepository fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Failure Type Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UncertaintyFailureTypeRepository getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(UncertaintyFactory.eINSTANCE.createUncertaintyFailureTypeRepository());
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

} //UncertaintyFailureTypeRepositoryTest
