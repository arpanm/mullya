import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { getEntity, updateEntity, createEntity, reset } from './remittance-details.reducer';
import { IRemittanceDetails } from 'app/shared/model/remittance-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { PGType } from 'app/shared/model/enumerations/pg-type.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export const RemittanceDetailsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const actors = useAppSelector(state => state.actor.entities);
  const orders = useAppSelector(state => state.order.entities);
  const remittanceDetailsEntity = useAppSelector(state => state.remittanceDetails.entity);
  const loading = useAppSelector(state => state.remittanceDetails.loading);
  const updating = useAppSelector(state => state.remittanceDetails.updating);
  const updateSuccess = useAppSelector(state => state.remittanceDetails.updateSuccess);
  const paymentTypeValues = Object.keys(PaymentType);
  const pGTypeValues = Object.keys(PGType);
  const paymentStatusValues = Object.keys(PaymentStatus);
  const handleClose = () => {
    props.history.push('/remittance-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getActors({}));
    dispatch(getOrders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...remittanceDetailsEntity,
      ...values,
      farmer: actors.find(it => it.id.toString() === values.farmer.toString()),
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
          ...remittanceDetailsEntity,
          farmer: remittanceDetailsEntity?.farmer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.remittanceDetails.home.createOrEditLabel" data-cy="RemittanceDetailsCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.remittanceDetails.home.createOrEditLabel">Create or edit a RemittanceDetails</Translate>
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
                  id="remittance-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.paymentType')}
                id="remittance-details-paymentType"
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
                label={translate('mulyaaApp.remittanceDetails.onlinePgType')}
                id="remittance-details-onlinePgType"
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
                label={translate('mulyaaApp.remittanceDetails.pgTxnId')}
                id="remittance-details-pgTxnId"
                name="pgTxnId"
                data-cy="pgTxnId"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.pgStatus')}
                id="remittance-details-pgStatus"
                name="pgStatus"
                data-cy="pgStatus"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.offlineTxnId')}
                id="remittance-details-offlineTxnId"
                name="offlineTxnId"
                data-cy="offlineTxnId"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.offlineTxnDetails')}
                id="remittance-details-offlineTxnDetails"
                name="offlineTxnDetails"
                data-cy="offlineTxnDetails"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.offlineTxnGivenBy')}
                id="remittance-details-offlineTxnGivenBy"
                name="offlineTxnGivenBy"
                data-cy="offlineTxnGivenBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.offlineTxnClearingStatus')}
                id="remittance-details-offlineTxnClearingStatus"
                name="offlineTxnClearingStatus"
                data-cy="offlineTxnClearingStatus"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.remittanceDate')}
                id="remittance-details-remittanceDate"
                name="remittanceDate"
                data-cy="remittanceDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.remittanceInitTime')}
                id="remittance-details-remittanceInitTime"
                name="remittanceInitTime"
                data-cy="remittanceInitTime"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.remittanceUpdateTime')}
                id="remittance-details-remittanceUpdateTime"
                name="remittanceUpdateTime"
                data-cy="remittanceUpdateTime"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.paymentStatus')}
                id="remittance-details-paymentStatus"
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
                label={translate('mulyaaApp.remittanceDetails.createdOn')}
                id="remittance-details-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.createdBy')}
                id="remittance-details-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.updatedOn')}
                id="remittance-details-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.remittanceDetails.updatedBy')}
                id="remittance-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                id="remittance-details-farmer"
                name="farmer"
                data-cy="farmer"
                label={translate('mulyaaApp.remittanceDetails.farmer')}
                type="select"
              >
                <option value="" key="0" />
                {actors
                  ? actors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/remittance-details" replace color="info">
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

export default RemittanceDetailsUpdate;
