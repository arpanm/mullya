import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRemittanceDetails } from 'app/shared/model/remittance-details.model';
import { getEntities as getRemittanceDetails } from 'app/entities/remittance-details/remittance-details.reducer';
import { IRequirement } from 'app/shared/model/requirement.model';
import { getEntities as getRequirements } from 'app/entities/requirement/requirement.reducer';
import { IBids } from 'app/shared/model/bids.model';
import { getEntities as getBids } from 'app/entities/bids/bids.reducer';
import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { IStock } from 'app/shared/model/stock.model';
import { getEntities as getStocks } from 'app/entities/stock/stock.reducer';
import { getEntity, updateEntity, createEntity, reset } from './order.reducer';
import { IOrder } from 'app/shared/model/order.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export const OrderUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const remittanceDetails = useAppSelector(state => state.remittanceDetails.entities);
  const requirements = useAppSelector(state => state.requirement.entities);
  const bids = useAppSelector(state => state.bids.entities);
  const actors = useAppSelector(state => state.actor.entities);
  const stocks = useAppSelector(state => state.stock.entities);
  const orderEntity = useAppSelector(state => state.order.entity);
  const loading = useAppSelector(state => state.order.loading);
  const updating = useAppSelector(state => state.order.updating);
  const updateSuccess = useAppSelector(state => state.order.updateSuccess);
  const orderStatusValues = Object.keys(OrderStatus);
  const handleClose = () => {
    props.history.push('/order');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRemittanceDetails({}));
    dispatch(getRequirements({}));
    dispatch(getBids({}));
    dispatch(getActors({}));
    dispatch(getStocks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orderEntity,
      ...values,
      remittances: mapIdList(values.remittances),
      requirement: requirements.find(it => it.id.toString() === values.requirement.toString()),
      bid: bids.find(it => it.id.toString() === values.bid.toString()),
      assignedAgent: actors.find(it => it.id.toString() === values.assignedAgent.toString()),
      stock: stocks.find(it => it.id.toString() === values.stock.toString()),
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
          status: 'New',
          ...orderEntity,
          remittances: orderEntity?.remittances?.map(e => e.id.toString()),
          requirement: orderEntity?.requirement?.id,
          bid: orderEntity?.bid?.id,
          assignedAgent: orderEntity?.assignedAgent?.id,
          stock: orderEntity?.stock?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.order.home.createOrEditLabel" data-cy="OrderCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.order.home.createOrEditLabel">Create or edit a Order</Translate>
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
                  id="order-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.order.acceptedPrice')}
                id="order-acceptedPrice"
                name="acceptedPrice"
                data-cy="acceptedPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.codAmount')}
                id="order-codAmount"
                name="codAmount"
                data-cy="codAmount"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.quantityKg')}
                id="order-quantityKg"
                name="quantityKg"
                data-cy="quantityKg"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.neededBy')}
                id="order-neededBy"
                name="neededBy"
                data-cy="neededBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.acceptedDeliveryDate')}
                id="order-acceptedDeliveryDate"
                name="acceptedDeliveryDate"
                data-cy="acceptedDeliveryDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.createdOn')}
                id="order-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.createdBy')}
                id="order-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.updatedOn')}
                id="order-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.order.updatedBy')}
                id="order-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField label={translate('mulyaaApp.order.status')} id="order-status" name="status" data-cy="status" type="select">
                {orderStatusValues.map(orderStatus => (
                  <option value={orderStatus} key={orderStatus}>
                    {translate('mulyaaApp.OrderStatus.' + orderStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.order.remittance')}
                id="order-remittance"
                data-cy="remittance"
                type="select"
                multiple
                name="remittances"
              >
                <option value="" key="0" />
                {remittanceDetails
                  ? remittanceDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-requirement"
                name="requirement"
                data-cy="requirement"
                label={translate('mulyaaApp.order.requirement')}
                type="select"
              >
                <option value="" key="0" />
                {requirements
                  ? requirements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="order-bid" name="bid" data-cy="bid" label={translate('mulyaaApp.order.bid')} type="select">
                <option value="" key="0" />
                {bids
                  ? bids.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-assignedAgent"
                name="assignedAgent"
                data-cy="assignedAgent"
                label={translate('mulyaaApp.order.assignedAgent')}
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
              <ValidatedField id="order-stock" name="stock" data-cy="stock" label={translate('mulyaaApp.order.stock')} type="select">
                <option value="" key="0" />
                {stocks
                  ? stocks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order" replace color="info">
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

export default OrderUpdate;
