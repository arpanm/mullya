import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { ICancellationDetails } from 'app/shared/model/cancellation-details.model';
import { getEntities as getCancellationDetails } from 'app/entities/cancellation-details/cancellation-details.reducer';
import { getEntity, updateEntity, createEntity, reset } from './delivery-details.reducer';
import { IDeliveryDetails } from 'app/shared/model/delivery-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { DeliveryType } from 'app/shared/model/enumerations/delivery-type.model';
import { DeliveryStatus } from 'app/shared/model/enumerations/delivery-status.model';

export const DeliveryDetailsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const addresses = useAppSelector(state => state.address.entities);
  const orders = useAppSelector(state => state.order.entities);
  const cancellationDetails = useAppSelector(state => state.cancellationDetails.entities);
  const deliveryDetailsEntity = useAppSelector(state => state.deliveryDetails.entity);
  const loading = useAppSelector(state => state.deliveryDetails.loading);
  const updating = useAppSelector(state => state.deliveryDetails.updating);
  const updateSuccess = useAppSelector(state => state.deliveryDetails.updateSuccess);
  const deliveryTypeValues = Object.keys(DeliveryType);
  const deliveryStatusValues = Object.keys(DeliveryStatus);
  const handleClose = () => {
    props.history.push('/delivery-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAddresses({}));
    dispatch(getOrders({}));
    dispatch(getCancellationDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...deliveryDetailsEntity,
      ...values,
      fromAddress: addresses.find(it => it.id.toString() === values.fromAddress.toString()),
      toAddress: addresses.find(it => it.id.toString() === values.toAddress.toString()),
      order: orders.find(it => it.id.toString() === values.order.toString()),
      cancellation: cancellationDetails.find(it => it.id.toString() === values.cancellation.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          deliveryType: 'FarmerToBuyer',
          deliveryStatus: 'PendingConfirmation',
          ...deliveryDetailsEntity,
          fromAddress: deliveryDetailsEntity?.fromAddress?.id,
          toAddress: deliveryDetailsEntity?.toAddress?.id,
          order: deliveryDetailsEntity?.order?.id,
          cancellation: deliveryDetailsEntity?.cancellation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.deliveryDetails.home.createOrEditLabel" data-cy="DeliveryDetailsCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.deliveryDetails.home.createOrEditLabel">Create or edit a DeliveryDetails</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="delivery-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.deliveryType')}
                id="delivery-details-deliveryType"
                name="deliveryType"
                data-cy="deliveryType"
                type="select"
              >
                {deliveryTypeValues.map(deliveryType => (
                  <option value={deliveryType} key={deliveryType}>
                    {translate('mulyaaApp.DeliveryType.' + deliveryType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.pickupDate')}
                id="delivery-details-pickupDate"
                name="pickupDate"
                data-cy="pickupDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.deliveryDate')}
                id="delivery-details-deliveryDate"
                name="deliveryDate"
                data-cy="deliveryDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.truckDetails')}
                id="delivery-details-truckDetails"
                name="truckDetails"
                data-cy="truckDetails"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.deliveryAgentPhone')}
                id="delivery-details-deliveryAgentPhone"
                name="deliveryAgentPhone"
                data-cy="deliveryAgentPhone"
                type="text"
                validate={{
                  min: { value: 1000000000, message: translate('entity.validation.min', { min: 1000000000 }) },
                  max: { value: 9999999999, message: translate('entity.validation.max', { max: 9999999999 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.pickupTime')}
                id="delivery-details-pickupTime"
                name="pickupTime"
                data-cy="pickupTime"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.deliveryTime')}
                id="delivery-details-deliveryTime"
                name="deliveryTime"
                data-cy="deliveryTime"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.createdOn')}
                id="delivery-details-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.createdBy')}
                id="delivery-details-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.updatedOn')}
                id="delivery-details-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.updatedBy')}
                id="delivery-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.deliveryDetails.deliveryStatus')}
                id="delivery-details-deliveryStatus"
                name="deliveryStatus"
                data-cy="deliveryStatus"
                type="select"
              >
                {deliveryStatusValues.map(deliveryStatus => (
                  <option value={deliveryStatus} key={deliveryStatus}>
                    {translate('mulyaaApp.DeliveryStatus.' + deliveryStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="delivery-details-fromAddress"
                name="fromAddress"
                data-cy="fromAddress"
                label={translate('mulyaaApp.deliveryDetails.fromAddress')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="delivery-details-toAddress"
                name="toAddress"
                data-cy="toAddress"
                label={translate('mulyaaApp.deliveryDetails.toAddress')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="delivery-details-order"
                name="order"
                data-cy="order"
                label={translate('mulyaaApp.deliveryDetails.order')}
                type="select"
              >
                <option value="" key="0" />
                {orders
                  ? orders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="delivery-details-cancellation"
                name="cancellation"
                data-cy="cancellation"
                label={translate('mulyaaApp.deliveryDetails.cancellation')}
                type="select"
              >
                <option value="" key="0" />
                {cancellationDetails
                  ? cancellationDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/delivery-details" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DeliveryDetailsUpdate;
