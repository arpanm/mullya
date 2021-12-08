import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './delivery-details.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DeliveryDetailsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const deliveryDetailsEntity = useAppSelector(state => state.deliveryDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deliveryDetailsDetailsHeading">
          <Translate contentKey="mullyaApp.deliveryDetails.detail.title">DeliveryDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.id}</dd>
          <dt>
            <span id="deliveryType">
              <Translate contentKey="mullyaApp.deliveryDetails.deliveryType">Delivery Type</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.deliveryType}</dd>
          <dt>
            <span id="pickupDate">
              <Translate contentKey="mullyaApp.deliveryDetails.pickupDate">Pickup Date</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.pickupDate}</dd>
          <dt>
            <span id="deliveryDate">
              <Translate contentKey="mullyaApp.deliveryDetails.deliveryDate">Delivery Date</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.deliveryDate}</dd>
          <dt>
            <span id="truckDetails">
              <Translate contentKey="mullyaApp.deliveryDetails.truckDetails">Truck Details</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.truckDetails}</dd>
          <dt>
            <span id="deliveryAgentPhone">
              <Translate contentKey="mullyaApp.deliveryDetails.deliveryAgentPhone">Delivery Agent Phone</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.deliveryAgentPhone}</dd>
          <dt>
            <span id="pickupTime">
              <Translate contentKey="mullyaApp.deliveryDetails.pickupTime">Pickup Time</Translate>
            </span>
          </dt>
          <dd>
            {deliveryDetailsEntity.pickupTime ? (
              <TextFormat value={deliveryDetailsEntity.pickupTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deliveryTime">
              <Translate contentKey="mullyaApp.deliveryDetails.deliveryTime">Delivery Time</Translate>
            </span>
          </dt>
          <dd>
            {deliveryDetailsEntity.deliveryTime ? (
              <TextFormat value={deliveryDetailsEntity.deliveryTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mullyaApp.deliveryDetails.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {deliveryDetailsEntity.createdOn ? (
              <TextFormat value={deliveryDetailsEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.deliveryDetails.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mullyaApp.deliveryDetails.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {deliveryDetailsEntity.updatedOn ? (
              <TextFormat value={deliveryDetailsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.deliveryDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.updatedBy}</dd>
          <dt>
            <span id="deliveryStatus">
              <Translate contentKey="mullyaApp.deliveryDetails.deliveryStatus">Delivery Status</Translate>
            </span>
          </dt>
          <dd>{deliveryDetailsEntity.deliveryStatus}</dd>
          <dt>
            <Translate contentKey="mullyaApp.deliveryDetails.fromAddress">From Address</Translate>
          </dt>
          <dd>{deliveryDetailsEntity.fromAddress ? deliveryDetailsEntity.fromAddress.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.deliveryDetails.toAddress">To Address</Translate>
          </dt>
          <dd>{deliveryDetailsEntity.toAddress ? deliveryDetailsEntity.toAddress.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.deliveryDetails.order">Order</Translate>
          </dt>
          <dd>{deliveryDetailsEntity.order ? deliveryDetailsEntity.order.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.deliveryDetails.cancellation">Cancellation</Translate>
          </dt>
          <dd>{deliveryDetailsEntity.cancellation ? deliveryDetailsEntity.cancellation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/delivery-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/delivery-details/${deliveryDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeliveryDetailsDetail;
