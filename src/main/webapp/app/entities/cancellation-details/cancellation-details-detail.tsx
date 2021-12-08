import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cancellation-details.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CancellationDetailsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const cancellationDetailsEntity = useAppSelector(state => state.cancellationDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cancellationDetailsDetailsHeading">
          <Translate contentKey="mullyaApp.cancellationDetails.detail.title">CancellationDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.id}</dd>
          <dt>
            <span id="cancelationType">
              <Translate contentKey="mullyaApp.cancellationDetails.cancelationType">Cancelation Type</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.cancelationType}</dd>
          <dt>
            <span id="cancellationReason">
              <Translate contentKey="mullyaApp.cancellationDetails.cancellationReason">Cancellation Reason</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.cancellationReason}</dd>
          <dt>
            <span id="cancellationDate">
              <Translate contentKey="mullyaApp.cancellationDetails.cancellationDate">Cancellation Date</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.cancellationDate}</dd>
          <dt>
            <span id="cancellationTime">
              <Translate contentKey="mullyaApp.cancellationDetails.cancellationTime">Cancellation Time</Translate>
            </span>
          </dt>
          <dd>
            {cancellationDetailsEntity.cancellationTime ? (
              <TextFormat value={cancellationDetailsEntity.cancellationTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="refundId">
              <Translate contentKey="mullyaApp.cancellationDetails.refundId">Refund Id</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.refundId}</dd>
          <dt>
            <span id="cancellationStatus">
              <Translate contentKey="mullyaApp.cancellationDetails.cancellationStatus">Cancellation Status</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.cancellationStatus}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mullyaApp.cancellationDetails.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {cancellationDetailsEntity.createdOn ? (
              <TextFormat value={cancellationDetailsEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.cancellationDetails.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mullyaApp.cancellationDetails.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {cancellationDetailsEntity.updatedOn ? (
              <TextFormat value={cancellationDetailsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.cancellationDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{cancellationDetailsEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mullyaApp.cancellationDetails.order">Order</Translate>
          </dt>
          <dd>{cancellationDetailsEntity.order ? cancellationDetailsEntity.order.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.cancellationDetails.approver">Approver</Translate>
          </dt>
          <dd>{cancellationDetailsEntity.approver ? cancellationDetailsEntity.approver.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.cancellationDetails.initiator">Initiator</Translate>
          </dt>
          <dd>{cancellationDetailsEntity.initiator ? cancellationDetailsEntity.initiator.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cancellation-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cancellation-details/${cancellationDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CancellationDetailsDetail;
