import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getEntities as getUsers } from 'app/entities/user/user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cancellation-details.reducer';
import { ICancellationDetails } from 'app/shared/model/cancellation-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { CancelationType } from 'app/shared/model/enumerations/cancelation-type.model';
import { CancellationStatus } from 'app/shared/model/enumerations/cancellation-status.model';

export const CancellationDetailsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const orders = useAppSelector(state => state.order.entities);
  const users = useAppSelector(state => state.user.entities);
  const cancellationDetailsEntity = useAppSelector(state => state.cancellationDetails.entity);
  const loading = useAppSelector(state => state.cancellationDetails.loading);
  const updating = useAppSelector(state => state.cancellationDetails.updating);
  const updateSuccess = useAppSelector(state => state.cancellationDetails.updateSuccess);
  const cancelationTypeValues = Object.keys(CancelationType);
  const cancellationStatusValues = Object.keys(CancellationStatus);
  const handleClose = () => {
    props.history.push('/cancellation-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOrders({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cancellationDetailsEntity,
      ...values,
      order: orders.find(it => it.id.toString() === values.order.toString()),
      approver: users.find(it => it.id.toString() === values.approver.toString()),
      initiator: users.find(it => it.id.toString() === values.initiator.toString()),
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
          cancelationType: 'ByFarmer',
          cancellationStatus: 'Init',
          ...cancellationDetailsEntity,
          order: cancellationDetailsEntity?.order?.id,
          approver: cancellationDetailsEntity?.approver?.id,
          initiator: cancellationDetailsEntity?.initiator?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.cancellationDetails.home.createOrEditLabel" data-cy="CancellationDetailsCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.cancellationDetails.home.createOrEditLabel">Create or edit a CancellationDetails</Translate>
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
                  id="cancellation-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.cancelationType')}
                id="cancellation-details-cancelationType"
                name="cancelationType"
                data-cy="cancelationType"
                type="select"
              >
                {cancelationTypeValues.map(cancelationType => (
                  <option value={cancelationType} key={cancelationType}>
                    {translate('mulyaaApp.CancelationType.' + cancelationType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.cancellationReason')}
                id="cancellation-details-cancellationReason"
                name="cancellationReason"
                data-cy="cancellationReason"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.cancellationDate')}
                id="cancellation-details-cancellationDate"
                name="cancellationDate"
                data-cy="cancellationDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.cancellationTime')}
                id="cancellation-details-cancellationTime"
                name="cancellationTime"
                data-cy="cancellationTime"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.refundId')}
                id="cancellation-details-refundId"
                name="refundId"
                data-cy="refundId"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.cancellationStatus')}
                id="cancellation-details-cancellationStatus"
                name="cancellationStatus"
                data-cy="cancellationStatus"
                type="select"
              >
                {cancellationStatusValues.map(cancellationStatus => (
                  <option value={cancellationStatus} key={cancellationStatus}>
                    {translate('mulyaaApp.CancellationStatus.' + cancellationStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.createdOn')}
                id="cancellation-details-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.createdBy')}
                id="cancellation-details-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.updatedOn')}
                id="cancellation-details-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.cancellationDetails.updatedBy')}
                id="cancellation-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                id="cancellation-details-order"
                name="order"
                data-cy="order"
                label={translate('mulyaaApp.cancellationDetails.order')}
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
                id="cancellation-details-approver"
                name="approver"
                data-cy="approver"
                label={translate('mulyaaApp.cancellationDetails.approver')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="cancellation-details-initiator"
                name="initiator"
                data-cy="initiator"
                label={translate('mulyaaApp.cancellationDetails.initiator')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cancellation-details" replace color="info">
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

export default CancellationDetailsUpdate;
