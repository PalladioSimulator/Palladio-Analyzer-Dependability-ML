/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.eclipse.emf.edit.provider.ViewerNotification;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

import tools.mdsd.modelingfoundations.identifier.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchitecturalCountermeasureItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitecturalCountermeasureItemProvider(AdapterFactory adapterFactory) {
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

			addArchitecturalPreconditionsPropertyDescriptor(object);
			addTargetUncertaintyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
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
				getString("_UI_ArchitecturalCountermeasure_architecturalPreconditions_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ArchitecturalCountermeasure_architecturalPreconditions_feature",
						"_UI_ArchitecturalCountermeasure_type"),
				UncertaintyPackage.Literals.ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS, true, false,
				true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Target Uncertainty feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTargetUncertaintyPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ArchitecturalCountermeasure_targetUncertainty_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_ArchitecturalCountermeasure_targetUncertainty_feature",
								"_UI_ArchitecturalCountermeasure_type"),
						UncertaintyPackage.Literals.ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY, true, false, true,
						null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(UncertaintyPackage.Literals.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ArchitecturalCountermeasure.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ArchitecturalCountermeasure"));
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
		String label = ((ArchitecturalCountermeasure) object).getEntityName();
		return label == null || label.length() == 0 ? getString("_UI_ArchitecturalCountermeasure_type")
				: getString("_UI_ArchitecturalCountermeasure_type") + " " + label;
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

		switch (notification.getFeatureID(ArchitecturalCountermeasure.class)) {
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
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

		newChildDescriptors.add(
				createChildParameter(UncertaintyPackage.Literals.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT,
						UncertaintyFactory.eINSTANCE.createDeterministicImprovement()));

		newChildDescriptors.add(
				createChildParameter(UncertaintyPackage.Literals.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT,
						UncertaintyFactory.eINSTANCE.createProbabilisticImprovement()));
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
