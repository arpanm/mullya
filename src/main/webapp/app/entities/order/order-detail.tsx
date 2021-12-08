import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './order.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrderDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const orderEntity = useAppSelector(state => state.order.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailsHeading">
          <Translate contentKey="mullyaApp.order.detail.title">Order</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderEntity.id}</dd>
          <dt>
            <span id="acceptedPrice">
              <Translate contentKey="mullyaApp.order.acceptedPrice">Accepted Price</Translate>
            </span>
          </dt>
          <dd>{orderEntity.acceptedPrice}</dd>
          <dt>
            <span id="codAmount">
              <Translate contentKey="mullyaApp.order.codAmount">Cod Amount</Translate>
            </span>
          </dt>
          <dd>{orderEntity.codAmount}</dd>
          <dt>
            <span id="quantityKg">
              <Translate contentKey="mullyaApp.order.quantityKg">Quantity Kg</Translate>
            </span>
          </dt>
          <dd>{orderEntity.quantityKg}</dd>
          <dt>
            <span id="neededBy">
              <Translate contentKey="mullyaApp.order.neededBy">Needed By</Translate>
            </span>
          </dt>
          <dd>{orderEntity.neededBy}</dd>
          <dt>
            <span id="acceptedDeliveryDate">
              <Translate contentKey="mullyaApp.order.acceptedDeliveryDate">Accepted Delivery Date</Translate>
            </span>
          </dt>
          <dd>{orderEntity.acceptedDeliveryDate}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mullyaApp.order.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{orderEntity.createdOn ? <TextFormat value={orderEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.order.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{orderEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mullyaApp.order.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{orderEntity.updatedOn ? <TextFormat value={orderEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.order.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{orderEntity.updatedBy}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mullyaApp.order.status">Status</Translate>
            </span>
          </dt>
          <dd>{orderEntity.status}</dd>
          <dt>
            <Translate contentKey="mullyaApp.order.remittance">Remittance</Translate>
          </dt>
          <dd>
            {orderEntity.remittances
              ? orderEntity.remittances.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {orderEntity.remittances && i === orderEntity.remittances.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="mullyaApp.order.requirement">Requirement</Translate>
          </dt>
          <dd>{orderEntity.requirement ? orderEntity.requirement.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.order.bid">Bid</Translate>
          </dt>
          <dd>{orderEntity.bid ? orderEntity.bid.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.order.assignedAgent">Assigned Agent</Translate>
          </dt>
          <dd>{orderEntity.assignedAgent ? orderEntity.assignedAgent.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.order.stock">Stock</Translate>
          </dt>
          <dd>{orderEntity.stock ? orderEntity.stock.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order/${orderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderDetail;
