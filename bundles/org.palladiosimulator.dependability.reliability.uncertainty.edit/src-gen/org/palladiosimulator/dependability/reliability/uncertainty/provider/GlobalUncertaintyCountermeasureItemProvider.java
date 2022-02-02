/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GlobalUncertaintyCountermeasureItemProvider extends ArchitecturalCountermeasureItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalUncertaintyCountermeasureItemProvider(AdapterFactory adapterFactory) {
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

			addImprovedUncertaintyModelPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Improved Uncertainty Model feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addImprovedUncertaintyModelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_GlobalUncertaintyCountermeasure_improvedUncertaintyModel_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_GlobalUncertaintyCountermeasure_improvedUncertaintyModel_feature",
						"_UI_GlobalUncertaintyCountermeasure_type"),
				UncertaintyPackage.Literals.GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL, true, false,
				true, null, null, null));
	}

	/**
	 * This returns GlobalUncertaintyCountermeasure.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/GlobalUncertaintyCountermeasure"));
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
		String label = ((GlobalUncertaintyCountermeasure) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_GlobalUncertaintyCountermeasure_type")
				: getString("_UI_GlobalUncertaintyCountermeasure_type") + " " + label;
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

}
