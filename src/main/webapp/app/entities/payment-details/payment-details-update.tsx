import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payment-details.reducer';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { PGType } from 'app/shared/model/enumerations/pg-type.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export const PaymentDetailsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const orders = useAppSelector(state => state.order.entities);
  const paymentDetailsEntity = useAppSelector(state => state.paymentDetails.entity);
  const loading = useAppSelector(state => state.paymentDetails.loading);
  const updating = useAppSelector(state => state.paymentDetails.updating);
  const updateSuccess = useAppSelector(state => state.paymentDetails.updateSuccess);
  const paymentTypeValues = Object.keys(PaymentType);
  const pGTypeValues = Object.keys(PGType);
  const paymentStatusValues = Object.keys(PaymentStatus);
  const handleClose = () => {
    props.history.push('/payment-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOrders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...paymentDetailsEntity,
      ...values,
      order: orders.find(it => it.id.toString() === values.order.toString()),
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
          paymentType: 'Cheque',
          onlinePgType: 'RazorPay',
          paymentStatus: 'Pending',
          ...paymentDetailsEntity,
          order: paymentDetailsEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.paymentDetails.home.createOrEditLabel" data-cy="PaymentDetailsCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.paymentDetails.home.createOrEditLabel">Create or edit a PaymentDetails</Translate>
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
                  id="payment-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.amount')}
                id="payment-details-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.paymentType')}
                id="payment-details-paymentType"
                name="paymentType"
                data-cy="paymentType"
                type="select"
              >
                {paymentTypeValues.map(paymentType => (
                  <option value={paymentType} key={paymentType}>
                    {translate('mulyaaApp.PaymentType.' + paymentType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.onlinePgType')}
                id="payment-details-onlinePgType"
                name="onlinePgType"
                data-cy="onlinePgType"
                type="select"
              >
                {pGTypeValues.map(pGType => (
                  <option value={pGType} key={pGType}>
                    {translate('mulyaaApp.PGType.' + pGType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.pgTxnId')}
                id="payment-details-pgTxnId"
                name="pgTxnId"
                data-cy="pgTxnId"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.pgStatus')}
                id="payment-details-pgStatus"
                name="pgStatus"
                data-cy="pgStatus"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.offlineTxnId')}
                id="payment-details-offlineTxnId"
                name="offlineTxnId"
                data-cy="offlineTxnId"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.offlineTxnDetails')}
                id="payment-details-offlineTxnDetails"
                name="offlineTxnDetails"
                data-cy="offlineTxnDetails"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.offlineTxnCollectedBy')}
                id="payment-details-offlineTxnCollectedBy"
                name="offlineTxnCollectedBy"
                data-cy="offlineTxnCollectedBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.offlineTxnClearingStatus')}
                id="payment-details-offlineTxnClearingStatus"
                name="offlineTxnClearingStatus"
                data-cy="offlineTxnClearingStatus"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.paymentDate')}
                id="payment-details-paymentDate"
                name="paymentDate"
                data-cy="paymentDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.paymentInitTime')}
                id="payment-details-paymentInitTime"
                name="paymentInitTime"
                data-cy="paymentInitTime"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.paymentUpdateTime')}
                id="payment-details-paymentUpdateTime"
                name="paymentUpdateTime"
                data-cy="paymentUpdateTime"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.paymentStatus')}
                id="payment-details-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate('mulyaaApp.PaymentStatus.' + paymentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.createdOn')}
                id="payment-details-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.createdBy')}
                id="payment-details-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.updatedOn')}
                id="payment-details-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.paymentDetails.updatedBy')}
                id="payment-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                id="payment-details-order"
                name="order"
                data-cy="order"
                label={translate('mulyaaApp.paymentDetails.order')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment-details" replace color="info">
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

export default PaymentDetailsUpdate;
