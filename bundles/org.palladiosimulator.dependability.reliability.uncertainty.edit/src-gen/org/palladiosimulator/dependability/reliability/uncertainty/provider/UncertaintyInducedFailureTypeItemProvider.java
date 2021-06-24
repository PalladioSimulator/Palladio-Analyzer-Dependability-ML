/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

import tools.mdsd.modelingfoundations.identifier.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class UncertaintyInducedFailureTypeItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintyInducedFailureTypeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addRefinesFailureTypePropertyDescriptor(object);
			addUncertaintyModelPropertyDescriptor(object);
			addFailureVariablePropertyDescriptor(object);
			addArchitecturalPreconditionsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Refines Failure Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRefinesFailureTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_UncertaintyInducedFailureType_refinesFailureType_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_UncertaintyInducedFailureType_refinesFailureType_feature",
								"_UI_UncertaintyInducedFailureType_type"),
						UncertaintyPackage.Literals.UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES_FAILURE_TYPE, true, false,
						true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Uncertainty Model feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUncertaintyModelPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_UncertaintyInducedFailureType_uncertaintyModel_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_UncertaintyInducedFailureType_uncertaintyModel_feature",
								"_UI_UncertaintyInducedFailureType_type"),
						UncertaintyPackage.Literals.UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL, true, false,
						true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Failure Variable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFailureVariablePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_UncertaintyInducedFailureType_failureVariable_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_UncertaintyInducedFailureType_failureVariable_feature",
								"_UI_UncertaintyInducedFailureType_type"),
						UncertaintyPackage.Literals.UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE, true, false,
						true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Architectural Preconditions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addArchitecturalPreconditionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_UncertaintyInducedFailureType_architecturalPreconditions_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_UncertaintyInducedFailureType_architecturalPreconditions_feature",
						"_UI_UncertaintyInducedFailureType_type"),
				UncertaintyPackage.Literals.UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS, true, false,
				true, null, null, null));
	}

	/**
	 * This returns UncertaintyInducedFailureType.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/UncertaintyInducedFailureType"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((UncertaintyInducedFailureType) object).getEntityName();
		return label == null || label.length() == 0 ? getString("_UI_UncertaintyInducedFailureType_type")
				: getString("_UI_UncertaintyInducedFailureType_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return UncertaintyEditPlugin.INSTANCE;
	}

}
